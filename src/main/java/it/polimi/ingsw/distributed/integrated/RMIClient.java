package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.guiController.ViewGui;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.NEW_TURN;

public class RMIClient extends UnicastRemoteObject implements Client, Runnable {

    private View view;
    private Server server;
    private String username;   //TODO: perchè l'username?
    private boolean connectionError = false;

    public RMIClient(View view, Server server) throws RemoteException{
        super();
        this.view = view;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            if (!server.canJoin()) System.out.println("The game has already started. Come back later.");
            else if (!connectionError) {
                try {
                    askUsername();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                try {
                    server.register(this, username);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                addObserver();
            }
            else System.out.println("Can't connect to server");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void addObserver(){
        //TODO: change method
        if (view instanceof CLI viewInstance) {
            viewInstance.addObserver((o, arg) -> {
                new Thread(()->{
                    try {
                        server.update(arg);
                    } catch (RemoteException e) {
                        System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                    }
                }).start();
            });
        } else {
            ViewGui viewInstance = (ViewGui) view;
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            });
        }

    }

    private void askUsername() throws RemoteException {
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printMessage("The username is not Available. Try Again.");
        } while(server.isUsernameAvailable(username));
        this.username = username;
        view.setThisUsername(username);
    }


    @Override
    public void update(GameModelView gameModelView, EventView event) throws RemoteException {
        //TODO: se non funziona il problema è con le parentesi (forse)
        if (event != NEW_TURN) view.ViewEventHandler(gameModelView, event);
        else {
            new Thread( () -> {
                view.newTurn(gameModelView);
            }).start();
        }
    }

    @Override
    public void receiveFromServer(String message) throws RemoteException {
        view.printMessage(message);
    }

    @Override
    public int askMaxNumOfPlayers() throws RemoteException{
        return view.askMaxNumOfPlayers();
    }

    @Override
    public void start() throws RemoteException {
        //TODO: non serve
    }


    public void startView() throws RemoteException{
        view.run();
    }

}
