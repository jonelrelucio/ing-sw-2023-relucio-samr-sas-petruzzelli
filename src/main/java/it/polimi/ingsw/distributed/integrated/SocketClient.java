package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.guiController.ViewGui;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.integrated.messages.*;

import java.rmi.RemoteException;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.NEW_TURN;

public class SocketClient implements Client, Runnable{

    /**
     * The view associated with this client
     */
    private View view;
    /**
     * The serverStub, which is used by the SocketClient instance to communicate with the server
     */
    private ServerStub server;
    /**
     * The username of the player
     */
    private String username;
    /**
     * True if there is a connection error
     */
    private boolean connectionError = false;

    /**
     * Initialized the SocketClient instance
     * @param serverStub the stub of the server
     * @param view the view used by this client
     */

    public SocketClient(ServerStub serverStub, View view){
        this.server = serverStub;
        this.view = view;
    }

    /**
     * Implementation of the run method of the Runnable interface
     */
    @Override
    public void run() {
        try {
            if(!server.canJoin()){
                System.out.println("The game has already started. Come back later");
            }else if (!connectionError){
                try{
                    askUsername();
                }catch (RemoteException e){
                    throw new RuntimeException(e);
                }
                try{
                    server.register(this, username);
                }catch(RemoteException e){
                    throw new RuntimeException(e);
                }
                if(!waitForPlayers()) return;
                addObserver();
                listenGameEvents();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an observer to this client
     */
    private void addObserver() {
        if (view instanceof CLI viewInstance) {
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
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
     * Code executed by the client during the match setup
     * @return true if it's waiting for players
     */

    private boolean waitForPlayers(){

        while(true){
            SimpleTextMessage message = (SimpleTextMessage) server.receiveObject();
            if(message.getMessageType() == MessageType.START_GAME_MESSAGE) return true;
            if(message.getMessageType() == MessageType.FIRST_PLAYER_MESSAGE){
                SimpleTextMessage numOfPlayersMessage = (SimpleTextMessage) server.receiveObject();
                if(numOfPlayersMessage.getMessageType() == MessageType.NUM_OF_PLAYERS_MESSAGE){
                    new Thread( () -> {
                        try{
                            int numOfPlayers = askMaxNumOfPlayers();
                            server.sendObject(numOfPlayers);
                        }catch(RemoteException e){
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
            }
            if(message.getMessageType() == MessageType.REMAINING_PLAYERS_MESSAGE) view.printMessage(message.getMessage());
            if(message.getMessageType() == MessageType.DEFAULT_MESSAGE) view.printMessage(message.getMessage());
            if(message.getMessageType() == MessageType.KICK_MESSAGE) {
                view.printMessage(message.getMessage());
                return false;
            }
        }
    }

    /**
     * Method used to receive update messages from the server
     */
    private void listenGameEvents(){
        while(true){
            UpdateMessage updateMessage = (UpdateMessage) server.receiveObject();
            try{
                update(updateMessage.getGameModelView(), updateMessage.getEventView());
            }catch(RemoteException e){
                System.err.println("Cannot update client "+e);
            }
        }

    }

    /**
     * Ask the username to the cli, which then is sent to the server
     * @throws RemoteException
     */
    private void askUsername() throws RemoteException{
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printMessage("The username is not Available. Try Again.");
        } while(server.isUsernameAvailable(username));
        this.username = username;
        view.setThisUsername(username);
    }


    /**
     * Updates the gameModelView
     * @param gameModelView the new GameModelView
     * @param event The event which updates the GameModelView
     * @throws RemoteException
     */
    @Override
    public void update(GameModelView gameModelView, EventView event) throws RemoteException {
        if (event != NEW_TURN) {
            new Thread(() -> {
                view.ViewEventHandler(gameModelView, event);
            }).start();
        }
        else {
            new Thread( () -> {
                view.newTurn(gameModelView);
            }).start();
        }
    }

    /**
     * Receives message from server
     * @param message Message received from the server
     * @throws RemoteException
     */
    @Override
    public void receiveFromServer(String message) throws RemoteException {
    }

    /**
     * Asks the number of players to the view
     * @return
     * @throws RemoteException
     */
    @Override
    public int askMaxNumOfPlayers() throws RemoteException {
        return view.askMaxNumOfPlayers();
    }

    /**
     * start
     * @throws RemoteException
     */
    @Override
    public void start() throws RemoteException {

    }

    /**
     * starts the view
     * @throws RemoteException
     */
    @Override
    public void startView() throws RemoteException {
        view.run();
    }
}
