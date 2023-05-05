package it.polimi.ingsw.distributed.newRmi;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable{

    private final View view;
    private String username;
    private Server server;
    private boolean connectionError = false;

    public ClientImpl(View view) throws RemoteException {
        super();
        this.view = view;
        start();
    }

    @Override
    public void start() throws RemoteException {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            connectionError = true;
        }
        try {
            assert registry != null;
            server =  (Server) registry.lookup("server");
        } catch (NotBoundException | RemoteException e) {
            connectionError = true;
        }

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
    }

    private void addObserver(){

        if (view instanceof CLI viewInstance) {
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            });
        } else {
            GUI viewInstance = (GUI) view;
            //TODO: View is does not extend Observable yet
//            viewInstance.addObserver((o, arg) -> {
//                try {
//                    server.update(arg);
//                } catch (RemoteException e) {
//                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
//                }
//            });
        }

    }


    private void askUsername() throws RemoteException {
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printMessage("The username is not Available. Try Again.");
        } while(server.isUsernameAvailable(username));
        this.username = username;
    }


    @Override
    public void update(GameEvent event) throws RemoteException {
        if (! (event instanceof GameModelView gameModelView)) throw new RuntimeException("Game Event is not instance of GameModelView");
        view.update(gameModelView);
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
    public void startView(){
        run();
    }

    @Override
    public void run() {
        view.run();
    }
}
