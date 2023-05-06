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
     * runs the view
     * locates the server rmi.
     * checks if client can join the server, by calling a remote method.
     * asks the username and registers the client to the server
     * Remote Exceptions are not thrown but captured and managed.
     */
    @Override
    public void start() {
        view.run();
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

    // TODO: change observer and observable classes to remove the use of instance of
    /**
     * Method called remotely by the observer in the server that observes its game model.
     * Updates the view game model view.
     * @param event     Game Event is of game model view instance
     */
    @Override
    public synchronized void update(GameEvent event) throws RemoteException {
        if (! (event instanceof GameModelView gameModelView)) throw new RuntimeException("Game Event is not instance of GameModelView");
        view.update(gameModelView);
    }

    /**
     * Receives a message from the server. Method called remotely.
     * @param message    Message received from the server.
     */
    @Override
    public synchronized void receiveFromServer(String message) throws RemoteException {
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
     * Method called remotely.
     * Server after meeting all conditions, calls this method to start the view.
     */
    @Override
    public void startView() throws RemoteException{
        view.startView();
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
    }



    /**
     * Adds a generic observer to the view instance and listens for view messages.
     * The messages are then sent as parameter to the server update remote method.
     */
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




}
