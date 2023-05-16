package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.ClientHandler;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
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

    private void manageConnection(Connection connection) throws RemoteException{
        if(!alreadyAsked){
            alreadyAsked = true;
            connection.sendMessageToClient("You are the first client to enter");
            //TODO: completare.....maxConnections = connection
            //maxConnections = clientHandlers.get(0).getClient().askMaxNumOfPlayers();
            connection.askMaxNumOfPlayers();
        }

        for(Connection conn : connections){
            if(maxConnections == 0){
                connection.sendMessageToClient(String.format("\"%s joined the waiting list. Waiting for %s to set number of players.", conn.getUsername()));
            }else if(connections.size() < maxConnections){
                connection.sendMessageToClient(String.format("%s joined the waiting list. %d more players remaining", conn.getUsername(), maxConnections - connections.size()));
            }

            if(maxConnections == 0){
                connection.sendMessageToClient("Please enter a maximum number of players: ");
            }
            if(maxConnections != 0 && maxConnections != connections.size()){
                startGame();
            }
        }
    }

    private void startGame() throws RemoteException{
        removeFromWaitingList();
        sendMessageToAllClients("Starting a new game...");
        ArrayList<String> playerList = createPlayerList();
        gameModel = new GameModel();
        updateClients();
        gameController = new Game(gameModel);
        gameController.initGameModel(playerList);
        gameController.start();
        for(Connection conn : connections){
            conn.startView();
        }
    }

    private void updateClients(){
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
                    conn.updateClient(arg);
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
            connections.get(connections.size()-1).sendMessageToClient("Lobby is Full. You have been kicked from the waiting list.");
            connections.remove(connections.size()-1);
        }
    }



    private void sendMessageToAllClients(String message) throws RemoteException {
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
    public void update(GameEvent arg) throws RemoteException {
        if (!(arg instanceof MessageEvent messageEvent)) throw new RuntimeException("Game event is not of instance messageEvent.");
        gameController.handleEvent(messageEvent);
    }
}
