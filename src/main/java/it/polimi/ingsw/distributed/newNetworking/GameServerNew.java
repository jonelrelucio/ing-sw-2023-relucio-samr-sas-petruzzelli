/*
package it.polimi.ingsw.distributed.newNetworking;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServerNew extends UnicastRemoteObject implements Server {

    private CircularArrayList<Connection> connections;

    public GameServerNew() throws RemoteException{
        connections = new CircularArrayList<>();
    }
    @Override
    public void register(Client client, String username) throws RemoteException {
        //registrazione RMI
    }

    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException {
        return false;
    }

    @Override
    public void start() throws RemoteException {

    }

    @Override
    public boolean canJoin() throws RemoteException {
        return false;
    }

    @Override
    public void update(GameEvent arg) throws RemoteException {

    }
}
*/
