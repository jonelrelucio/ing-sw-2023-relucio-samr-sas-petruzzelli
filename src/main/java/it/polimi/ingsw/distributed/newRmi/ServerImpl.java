package it.polimi.ingsw.distributed.newRmi;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.ClientHandler;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.model.GameModel;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private final ArrayList<ClientHandler> clientHandlers;
    private GameModel gameModel;
    private Game gameController;
    private int maxConnections = 0;

    public ServerImpl() throws RemoteException {
        super();
        clientHandlers = new ArrayList<>();
    }

    public void start() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);
        try {
            registry.rebind("server", this);
        } catch (RemoteException e) {
            System.out.println("Can't start the server.");
        }
    }

    @Override
    public void register(Client client, String username) throws RemoteException {
        // TODO:  check if it is thread safe or not;
        ClientHandler newClientHandler = new ClientHandler(client, username);
        clientHandlers.add(newClientHandler);
        checkConnections(client, newClientHandler);
    }

    private void checkConnections(Client client, ClientHandler newClientHandler) throws RemoteException {
        sendMessageToClient(clientHandlers.get(0).getClient(), "You are the first client to enter");
        int maxConnections = clientHandlers.get(0).getClient().askMaxNumOfPlayers();
        for (ClientHandler clientHandler : clientHandlers){
            sendMessageToClient(clientHandler.getClient(), String.format("%s has entered the waiting room.", newClientHandler.getUsername() ));
            if (maxConnections == 0) sendMessageToClient(clientHandler.getClient(), String.format("Added to the waiting list. Waiting for %s to set number of players.", clientHandlers.get(0).getUsername() ));
            else if (clientHandlers.size() < maxConnections ) sendMessageToClient(clientHandler.getClient(), String.format("%d more players remaining", maxConnections - clientHandlers.size() ));
        }
        if(maxConnections != 0) startGame();
    }

    private void startGame() throws RemoteException {
        removeFromWaitingList();
        sendMessageToAllClients("Starting a new Game...");
        ArrayList<String> playerList = createPlayerList();
        gameModel = new GameModel();
        gameController = new Game(gameModel);
        gameController.initGameModel(playerList);
        gameController.start();
    }

    private ArrayList<String> createPlayerList() {
        ArrayList<String> playerList = new ArrayList<>();
        for (ClientHandler clientHandler : clientHandlers) {
            playerList.add(clientHandler.getUsername());
        }
        return playerList;
    }


    private void removeFromWaitingList() throws RemoteException {
        while (clientHandlers.size() != maxConnections){
            sendMessageToClient(clientHandlers.get(clientHandlers.size()-1).getClient(), "Lobby is Full. You have been kicked from the waiting list.");
            clientHandlers.remove(clientHandlers.size()-1);
        }
    }

    private void sendMessageToClient(Client client, String message) throws RemoteException {
        client.receiveFromServer(message);
    }

    private void sendMessageToAllClients(String message) throws RemoteException {
        for (ClientHandler clientHandler : clientHandlers ){
            clientHandler.getClient().receiveFromServer(message);
        }
    }

    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException{
        for (ClientHandler clientHandler : clientHandlers){
            if (clientHandler.getUsername().equals(username)) return false;
        }
        return true;
    }
}
