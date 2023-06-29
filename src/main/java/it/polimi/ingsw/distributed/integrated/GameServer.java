package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.events.controllerEvents.EventController;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.distributed.integrated.messages.Message;
import it.polimi.ingsw.distributed.integrated.messages.MessageType;
import it.polimi.ingsw.distributed.integrated.messages.SimpleTextMessage;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.SHOW_LAST_MESSAGES;


/**
 * The main server class that manages the connections with the clients
 * Implements the Unicast Remote Object
 */
public class GameServer extends UnicastRemoteObject implements Server {

    /**
     * A circular arrayList of Connections
     */
    private CircularArrayList<Connection> connections;

    /**
     * The reference to the main Game Model
     */
    private GameModel gameModel;

    /**
     * The reference to the game controller
     */
    private Game gameController;

    /**
     * the max number of connections
     */
    private int maxConnections = 0;

    /**
     * a boolean value that indicates if the first player has been asked the max num of players already
     */
    private boolean alreadyAsked = false;

    /**
     * Constructor
     * @throws RemoteException  throws exception if fails to create game server
     */
    public GameServer() throws RemoteException{
        this.connections = new CircularArrayList<>();
    }

    /**
     * Getter for the circular array list of the connections
     * @return  returns the circular array list of connections
     */
    public CircularArrayList<Connection> getConnections(){
        return connections;
    }

    /**
     * Registers the client to Server
     * @param client            The client to be saved in the server
     * @param username          The username of the client
     * @throws RemoteException  Throes when fail to register
     */
    @Override
    public void register(Client client, String username) throws RemoteException {
        RMIConnection connection = new RMIConnection(client, username);
        try{
            System.out.println("Connected rmi client with ip: " + getClientHost());
        }catch(Exception e){}
        connections.add(connection);
        manageConnection(connection);

    }

    /**
     * Manages connection with all the clients
     * @param connection        the connection and its client
     * @throws RemoteException  throes exception when fail to manage clients
     */
    public void manageConnection(Connection connection) throws RemoteException{
        if(!alreadyAsked){
            alreadyAsked = true;
            connection.sendMessageToClient(new SimpleTextMessage(MessageType.FIRST_PLAYER_MESSAGE, "You are the first client to enter"));
            maxConnections = connection.askMaxNumOfPlayers();
            if ( connections.size() >= maxConnections){
                startGame();
                return;
            }
        }
        for(Connection conn : connections){
            if(maxConnections == 0){
                MessageType messageType = MessageType.REMAINING_PLAYERS_MESSAGE;
                String messageText = String.format("%s joined the waiting list. Waiting for %s to set number of players.", connection.getUsername(), connections.get(0).getUsername());
                System.out.println(messageText);
                conn.sendMessageToClient(new SimpleTextMessage(messageType, messageText));
            }else if(connections.size() < maxConnections){
                MessageType messageType = MessageType.REMAINING_PLAYERS_MESSAGE;
                String messageText = String.format("%s joined the waiting list. %d more players remaining", connection.getUsername(), maxConnections - connections.size());
                System.out.println(messageText);
                conn.sendMessageToClient(new SimpleTextMessage(messageType, messageText));
            }
        }

        if(maxConnections == 0){
            MessageType messageType = MessageType.DEFAULT_MESSAGE;
            String messageText = "Please enter a maximum number of players: ";
            connections.get(0).sendMessageToClient(new SimpleTextMessage(messageType, messageText));
        }
        if(maxConnections != 0 && connections.size() >= maxConnections){
            startGame();
        }

    }

    /**
     * Starts the game
     * @throws RemoteException  throws when fail to start game
     */
    private void startGame() throws RemoteException{
        removeFromWaitingList();
        sendMessageToAllClients(new SimpleTextMessage(MessageType.START_GAME_MESSAGE, "Starting a new game..."));
        ArrayList<String> playerList = createPlayerList();
        gameModel = new GameModel();
        updateClients();
        gameController = new Game(gameModel);
        gameController.initGameModel(playerList);
        gameController.start();
        startClientSocketEventListenerThreads();
    }

    /**
     * Adds the observers to the connections
     */
    public void updateClients(){
        for(Connection conn : connections){
            gameModel.addObserver((o, arg)->{
                try{
                    conn.updateClient(new GameModelView(gameModel), arg);
                }catch(RemoteException e){
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
        }
    }

    /**
     * Creates the player list from the connected clients and returns it
     * @return  the player list
     */
    private ArrayList<String> createPlayerList() {
        ArrayList<String> playerList = new ArrayList<>();
        for(Connection conn : connections){
            playerList.add(conn.getUsername());
        }
        return playerList;
    }

    /**
     * Disconnects clients from the server by removing them from the Connection list
     * @throws RemoteException  throws exception when fail to disconnect the client
     */
    private void removeFromWaitingList() throws RemoteException {
        while(connections.size() != maxConnections){
            connections.get(connections.size()-1).sendMessageToClient(new SimpleTextMessage(MessageType.KICK_MESSAGE, "Lobby is Full. You have been kicked from the waiting list."));
            connections.remove(connections.size()-1);
        }
    }

    /**
     * Starts the Client Socket Event Listener threads for each client
     */
    private void startClientSocketEventListenerThreads(){
        for(Connection conn : getConnections()){
            if(conn instanceof SocketConnection){
                Thread socketClientListener = new Thread(new ClientSocketEventListener(this, (SocketConnection) conn));
                socketClientListener.start();
            }
        }
    }

    /**
     * Sends a message of type Message to all clients
     * @param message           the message to bee sent to all clients
     * @throws RemoteException  throws when fails to send message to all clients
     */
    private void sendMessageToAllClients(Message message) throws RemoteException {
        for(Connection conn : connections){
            conn.sendMessageToClient(message);
        }
    }

    /**
     * Checks if username chosen by a client is available by checking if the other clients have the username already taken
     * @param username          username to check
     * @return                  true if username is available, false otherwise
     * @throws RemoteException  throes exception when fails to check
     */
    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException {
        for(Connection conn : connections){
            if(conn.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Start
     * @throws RemoteException  throes Exception when fails to start
     */
    @Override
    public void start() throws RemoteException {

    }

    /**
     * Check if the game has already started
     * @return true if the game has not yet started
     * @throws RemoteException  throes exception when fail to check join availability
     */
    @Override
    public boolean canJoin() throws RemoteException {
        return maxConnections > connections.size() || maxConnections == 0;
    }

    /**
     * Updates the game model through the game controller handler
     * @param messageEvent      the message event
     * @throws RemoteException  throes exception if fail to send the chat
     */
    @Override
    public void update(MessageEvent messageEvent) throws RemoteException {
        if (messageEvent.getEventType() == EventController.SHOW_CHAT) {
            Connection connection = connections.stream().filter(c -> c.getUsername().equals(messageEvent.getMessage())).findFirst().orElse(null);
            if (connection != null) {
                connection.updateClient(new GameModelView(gameModel), SHOW_LAST_MESSAGES);
            }
        } else {
            gameController.handleEvent(messageEvent);
        }
    }
}
