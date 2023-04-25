package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.util.CircularArrayList;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private GameModel gameModel;

    public ServerImpl(GameModel gameModel) throws RemoteException {
        super();
    }

    public ServerImpl(GameModel gameModel, int port) throws RemoteException{
        super(port);
    }

    public ServerImpl(GameModel gameModel, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException{
        super(port, csf, ssf);
    }



    @Override
    public void register(Client client) throws RemoteException {


    }

    @Override
    public void update(Client client, GameEvent arg) throws RemoteException {

    }
}
