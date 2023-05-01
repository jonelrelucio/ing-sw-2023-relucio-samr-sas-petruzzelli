package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.model.GameModelView;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerRmi extends UnicastRemoteObject implements Server {

    private GameModel gameModel;
    private Game controller;
    private ArrayList<Client> clients;

    public ServerRmi(GameModel gameModel) throws RemoteException {
        super();
        this.gameModel = gameModel;
        clients = new ArrayList<>();
    }

    public ServerRmi() throws RemoteException{ super();}

    public ServerRmi(GameModel gameModel, int port) throws RemoteException{ super(port);}

    public ServerRmi(GameModel gameModel, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException{ super(port, csf, ssf); }


    @Override
    public void register(Client client) throws RemoteException {
        if(clients.isEmpty()){
            clients.add(client);
            controller = new Game(gameModel);
            gameModel.addObserver((o, arg) -> {
                try {
                    System.out.println("CALLED 2");
                    client.update(new GameModelView(gameModel), arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
        }else if(clients.size() < gameModel.getNumOfPlayer()){
            clients.add(client);
            this.gameModel.addObserver((o, arg) -> {
                try {
                    client.update(new GameModelView(gameModel), arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
        } else {

        }

    }

    @Override
    public void update(GameEvent event) throws RemoteException {
        this.controller.eventHandler(event);
    }

}
