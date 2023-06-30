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

/**
 * RMI class client that implements the Client interface
 */
public class RMIClient extends UnicastRemoteObject implements Client, Runnable {
    /**
     * The view reference (could be CLI or ViewGui)
     */
    private View view;

    /**
     * The reference to the server stub
     */
    private Server server;

    /**
     * The username of the client
     */
    private String username;

    /**
     * boolean that indicates a connection error
     */
    private boolean connectionError = false;


    /**
     * The constructor
     * @param view      The reference to the view
     * @param server    The reference to the server stub
     * @throws RemoteException  when fails to create rmi client
     */
    public RMIClient(View view, Server server) throws RemoteException{
        super();
        this.view = view;
        this.server = server;
    }

    /**
     * Overrides the runnable interface
     */
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

    /**
     * Adds observer to the view instance
     */
    private void addObserver(){
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

    /**
     * Asks username
     * @throws RemoteException when fails to ask username
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
     * calls the view event handler after receiving
     * @param gameModelView the game Model view received from the server
     * @param event event received from the server
     * @throws RemoteException if fails to update
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
     * receives message from server
     * @param message   message string
     * @throws RemoteException  throes
     */
    @Override
    public void receiveFromServer(String message) throws RemoteException {
        view.printMessage(message);
    }

    /**
     * asks the max num of players
     * @return the max num of players
     * @throws RemoteException  when fails
     */
    @Override
    public int askMaxNumOfPlayers() throws RemoteException{
        return view.askMaxNumOfPlayers();
    }

    /**
     * starts
     * @throws RemoteException when fails to start
     */
    @Override
    public void start() throws RemoteException {

    }

    /**
     * starts the view
     * @throws RemoteException when fails
     */
    public void startView() throws RemoteException{
        view.run();
    }

}
