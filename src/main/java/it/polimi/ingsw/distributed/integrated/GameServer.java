package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.ClientHandler;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
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

public class GameServer extends UnicastRemoteObject implements Server {

    private CircularArrayList<Connection> connections;
    private GameModel gameModel;
    private Game gameController;
    private int maxConnections = 0;
    private boolean alreadyAsked = false;

    public GameServer() throws RemoteException{
        this.connections = new CircularArrayList<>();
    }

    public CircularArrayList<Connection> getConnections(){
        return connections;
    }

    @Override
    public void register(Client client, String username) throws RemoteException {
        RMIConnection connection = new RMIConnection(client, username);
        connections.add(connection);
        manageConnection(connection);
    }

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
                //connection.sendMessageToClient(String.format("%s joined the waiting list. Waiting for %s to set number of players.", conn.getUsername(), conn.getUsername()));
                MessageType messageType = MessageType.REMAINING_PLAYERS_MESSAGE;
                String messageText = String.format("%s joined the waiting list. Waiting for %s to set number of players.", connection.getUsername(), connections.get(0).getUsername());
                System.out.println(messageText);
                conn.sendMessageToClient(new SimpleTextMessage(messageType, messageText));
            }else if(connections.size() < maxConnections){
                //connection.sendMessageToClient(String.format("%s joined the waiting list. %d more players remaining", conn.getUsername(), maxConnections - connections.size()));
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

    private void startGame() throws RemoteException{
        removeFromWaitingList();
        //sendMessageToAllClients("Starting a new game...");
        sendMessageToAllClients(new SimpleTextMessage(MessageType.START_GAME_MESSAGE, "Starting a new game..."));
        ArrayList<String> playerList = createPlayerList();
        gameModel = new GameModel();
        updateClients();
        gameController = new Game(gameModel);
        gameController.initGameModel(playerList);
        gameController.start();
        /*
        for(Connection conn : connections){
            conn.startView();
        }
         */
        startClientSocketEventListenerThreads();
    }

    public void updateClients(){
        /*
        for(Connection conn : connections){
            gameModel.addObserver((o, arg) -> {
                try {
                    clientHandler.getClient().update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
        }

         */
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

    private ArrayList<String> createPlayerList() {
        ArrayList<String> playerList = new ArrayList<>();
        for(Connection conn : connections){
            playerList.add(conn.getUsername());
        }
        return playerList;
    }

    private void removeFromWaitingList() throws RemoteException {
        while(connections.size() != maxConnections){
            connections.get(connections.size()-1).sendMessageToClient(new SimpleTextMessage(MessageType.KICK_MESSAGE, "Lobby is Full. You have been kicked from the waiting list."));
            connections.remove(connections.size()-1);
        }
    }



    private void sendMessageToAllClients(String message) throws RemoteException {
        for(Connection conn : connections){
            conn.sendMessageToClient(message);
        }
    }

    private void startClientSocketEventListenerThreads(){
        for(Connection conn : getConnections()){
            if(conn instanceof SocketConnection){
                //Make thread and start it
                Thread socketClientListener = new Thread(new ClientSocketEventListener(this, (SocketConnection) conn));
                socketClientListener.start();
            }
        }
    }

    private void sendMessageToAllClients(Message message) throws RemoteException {
        for(Connection conn : connections){
            conn.sendMessageToClient(message);
        }
    }

    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException {
        for(Connection conn : connections){
            if(conn.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() throws RemoteException {
        //SI POTREBBE ELIMINARE
    }

    @Override
    public boolean canJoin() throws RemoteException {
        return maxConnections > connections.size() || maxConnections == 0;
    }

    @Override
    public void update(MessageEvent messageEvent) throws RemoteException {
        gameController.handleEvent(messageEvent);
    }
}
