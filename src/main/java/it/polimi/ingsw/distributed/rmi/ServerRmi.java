package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.ViewEvents.FullGameLobbyEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerRmi extends UnicastRemoteObject implements Server {

    private ArrayList<Client> clients = new ArrayList<>();
    private int maxNumOfClients;
    private GameModel gameModel;
    private Game controller;

    public ServerRmi(GameModel gameModel) throws RemoteException {
        super();
        this.gameModel = gameModel;
    }

    public ServerRmi() throws RemoteException{ super();}

    public ServerRmi(GameModel gameModel, int port) throws RemoteException{ super(port);}

    public ServerRmi(GameModel gameModel, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException{ super(port, csf, ssf); }


    @Override
    public void register(Client client) throws RemoteException {


            clients.add(client);
            controller = new Game(gameModel);
            gameModel.addObserver((o, arg) -> {
                try {
                    client.update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });


    }

    @Override
    public void update( GameEvent event) throws RemoteException {
        this.controller.eventHandler( event);
    }

    @Override
    public boolean isFirst(Client client) {
        return clients.isEmpty();
    }

    @Override
    public void setMaxNumOfClients(int numOfClients) throws RemoteException {
        this.maxNumOfClients = numOfClients;

        while (clients.size() != maxNumOfClients){
            clients.get(clients.size()-1).printFullLobby();
            clients.remove(clients.size()-1);
        }
    }

    public boolean canConnect(Client client){
        return clients.size() < maxNumOfClients;
    }

    @Override
    public ArrayList<Client> getClients() {
        return clients;
    }


}
