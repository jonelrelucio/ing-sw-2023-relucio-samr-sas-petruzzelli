package it.polimi.ingsw.distributed.newRmi;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ArrayBlockingQueue;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable{

    private final View view;
    private String username;
    private Server server;
    private boolean connectionError = false;

    /**
     * Client Impl constructor
     * @param view  The view instance (could be a cli or gui view instance)
     */
    public ClientImpl(View view) throws RemoteException {
        super();
        this.view = view;
    }

    /**
     * Starts the client:
     * locates the server rmi.
     * checks if client can join the server, by calling a remote method.
     * asks the username and registers the client to the server
     * Remote Exceptions are not thrown but captured and managed.
     */
    @Override
    public void start() {
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

    @Override
    public void startView() throws RemoteException {

    }

    // TODO: change observer and observable classes to remove the use of instance of
    /**
     * Method called remotely by the observer in the server that observes its game model.
     * Updates the view game model view.
     * @param gameModelView     game model view instance
     */
    @Override
    public void update(GameModelView gameModelView, EventView event) throws RemoteException {
        if (event != NEW_TURN) view.ViewEventHandler(gameModelView, event);
        else {
            new Thread( () -> {
                view.newTurn(gameModelView);
            }).start();
        }
    }

    /**
     * Receives a message from the server. Method called remotely.
     * @param message    Message received from the server.
     */
    @Override
    public void receiveFromServer(String message) throws RemoteException {
            view.printMessage(message);
    }

    /**
     * Asks the user for the maximum number of players. Method called remotely by the server.
     * Server checks if the client is the first to connect to the server and the server invokes this method remotely.
     * @return  the max number of player as int.
     */
    @Override
    public int askMaxNumOfPlayers() throws RemoteException{
        return view.askMaxNumOfPlayers();
    }

    /**
     * runs the client as a new thread
     * calls the start method.
     */
    @Override
    public void run() {
        start();
    }


    /**
     * asks user for its username and calls remote method if the username is available to choose.
     */
    private void askUsername() throws RemoteException {
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printMessage("The username is not Available. Try Again.");
        } while(server.isUsernameAvailable(username));
        this.username = username;
        view.setThisUsername(username);
    }



    /**
     * Adds a generic observer to the view instance and listens for view messages.
     * The messages are then sent as parameter to the server update remote method.
     */
    private void addObserver(){

        if (view instanceof CLI viewInstance) {
            viewInstance.addObserver((o, arg) -> {
                new Thread( () -> {
                    try {
                        server.update(arg);
                    } catch (RemoteException e) {
                        System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                    }
                }).start();
            });
        } else {
            GUI viewInstance = (GUI) view;
            //TODO: GUI view does not extend Observable yet
//            viewInstance.addObserver((o, arg) -> {
//                try {
//                    server.update(arg);
//                } catch (RemoteException e) {
//                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
//                }
//            });
        }

    }

    public void receiveChat(ArrayBlockingQueue<String> chat) throws RemoteException {
        for (String m : chat) {
            String[] message = m.split(":");

            String color = "\033[0;33m";
            if (message[0].equals(username)) {
                color = "\033[0;34m";
            }

            System.out.println(color + message[0] + ":" + "\033[0m" + message[1]);
        }
    }


}
