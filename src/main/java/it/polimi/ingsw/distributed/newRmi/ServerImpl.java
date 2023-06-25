package it.polimi.ingsw.distributed.newRmi;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.ClientHandler;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.events.controllerEvents.EventController;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.model.GameModel;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private final ArrayList<ClientHandler> clientHandlers;
    private GameModel gameModel;
    private Game gameController;
    private int maxConnections = 0;
    private boolean alreadyAsked = false;

    /**
     * Constructor of ServerImpl
     * Creates a new ArrayList of Client Handlers
     * starts the RMI registry with sstart()
     */
    public ServerImpl() throws RemoteException {
        super();
        clientHandlers = new ArrayList<>();
        start();
    }

    /**
     * Starts an RMI registry on port 1099, and binds the server object to the remote object name "server".
     */
    public void start() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);
        try {
            registry.rebind("server", this);
        } catch (RemoteException e) {
            System.out.println("Can't start the server.");
        }
    }


    /**
     * Registers the client to the server.
     * The client and the username of the client is added to the list of client handlers.
     * @param client        client to be registered to the server and added to the arraylist of client handlers.
     * @param username      username of the client, to also be added to the client handler
     */
    @Override
    public void register(Client client, String username) throws RemoteException {
        // TODO:  check if it is thread safe or not;
        ClientHandler newClientHandler = new ClientHandler(client, username);
        clientHandlers.add(newClientHandler);
        manageConnection( newClientHandler);

    }

    /**
     * Manages connection to new clients.
     * If the client is the first to connect to the server. It asks it for the number of maximum connections.
     * if the client is not the first to connect, the client either waits for the first player input or waits for the connections to reach its maximum connections.
     * Whe all conditions have been met, calls the startGame() method, to message the clients that the game has started.
     * @param newClientHandler  the client that has just connected
     */
    private void manageConnection( ClientHandler newClientHandler) throws RemoteException {
        if ( !alreadyAsked) {
            alreadyAsked = true;
            sendMessageToClient(clientHandlers.get(0).getClient(), "You are the first client to enter");
            maxConnections = clientHandlers.get(0).getClient().askMaxNumOfPlayers();
            if ( clientHandlers.size() >= maxConnections) {
                startGame();
                return;
            }
        }
        for (ClientHandler clientHandler : clientHandlers){
            if (maxConnections == 0) {
                sendMessageToClient(clientHandler.getClient(), String.format("%s joined the waiting list. Waiting for %s to set number of players.",newClientHandler.getUsername(), clientHandlers.get(0).getUsername() ));
            }
            else if (clientHandlers.size() < maxConnections ) sendMessageToClient(clientHandler.getClient(), String.format("%s joined the waiting list. %d more players remaining", newClientHandler.getUsername(), maxConnections - clientHandlers.size() ));
        }
        if(maxConnections == 0) sendMessageToClient(clientHandlers.get(0).getClient(), "Please enter a maximum number of players: ");
        if(maxConnections != 0 && clientHandlers.size() >= maxConnections) startGame();
    }

    /**
     * Creates new game:
     * Calls removeFromWaitingList method
     * Calls updateClients method
     * Starts the game controller, which sets all the starting values of the game model.
     * Starts the view for all clients.
     */
    private void startGame() throws RemoteException {
        if (clientHandlers.size() > maxConnections ) removeFromWaitingList();
        sendMessageToAllClients("Starting a new Game...");
        ArrayList<String> playerList = createPlayerList();
        gameModel = new GameModel();
        updateClients();
        gameController = new Game(gameModel);
        gameController.start();
        gameController.initGameModel(playerList);
    }

    /**
     * Adds observers to the game model, which is an observable.
     * When the game model receives an update, the clients are updated too.
     */
    private void updateClients(){
        for (ClientHandler clientHandler : clientHandlers ) {
            gameModel.addObserver((o, arg) -> {
                try {
                    clientHandler.getClient().update(new GameModelView(gameModel), arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
        }
    }

    /**
     * Obser
     */

    // TODO: remove the shuffle method from the game model
    /**
     * Shuffles the arraylist of clients.
     * creates an arraylist of client usernames
     * @return  the arraylist of client usernames
     */
    private ArrayList<String> createPlayerList() {
        Collections.shuffle(clientHandlers);
        ArrayList<String> playerList = new ArrayList<>();
        for (ClientHandler clientHandler : clientHandlers) {
            playerList.add(clientHandler.getUsername());
        }
        return playerList;
    }

    /**
     * Removes the excess connected clients from the clientHandlers list
     * Sends to the kicked clients a "lobby is full" message
     */
    private void removeFromWaitingList() throws RemoteException {
        while (clientHandlers.size() != maxConnections){
            sendMessageToClient(clientHandlers.get(clientHandlers.size()-1).getClient(), "Lobby is Full. You have been kicked from the waiting list.");
            clientHandlers.remove(clientHandlers.size()-1);
        }
    }

    /**
     * Sends a generic message to a specific client.
     * @param client    The specific client to which the message is sent
     * @param message   The message to be sent to the client
     */
    private void sendMessageToClient(Client client, String message) throws RemoteException {
        client.receiveFromServer(message);
    }

    /**
     * Sends a message to all connected clients.
     * @param message   The message to be sent to all clients
     */
    private void sendMessageToAllClients(String message) throws RemoteException {
        for (ClientHandler clientHandler : clientHandlers ){
            clientHandler.getClient().receiveFromServer(message);
        }
    }

    /**
     * Checks of the username has already been chosen by another client. Method is called remotely
     * @param username  username to be checked
     * @return          true if the username is available
     */
    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException{
        for (ClientHandler clientHandler : clientHandlers){
            if (clientHandler.getUsername().equals(username)) return true;
        }
        return false;
    }

    /**
     * Checks if a client can connect to the server. The method is called remotely.
     * @return  true if the max connections is greater than the connected clients or when the max connection has not been set yet.
     */
    @Override
    public boolean canJoin() throws RemoteException {
        return maxConnections > clientHandlers.size() || maxConnections == 0;
    }

    /**
     * Method called remotely.
     * receives a message event that is sent to the game controller which manages the message event
     *
     * @param messageEvent message event received from the client, which got it from its observable view.
     */
    @Override
    public void update( MessageEvent messageEvent) throws RemoteException{
        new Thread( () -> {
            if (messageEvent.getEventType().equals(EventController.SHOW_CHAT)) {
                Client client = clientHandlers.stream().filter(u -> u.getUsername().equals(messageEvent.getMessage())).map(ClientHandler::getClient).findFirst().orElse(null);
                if (client != null) {
                    try {
                        client.receiveChat(gameController.getChatMessages());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("receiveChat method call error: Client not found");
                }
            } else {
                gameController.handleEvent(messageEvent);
            }
        }).start();
    }
}