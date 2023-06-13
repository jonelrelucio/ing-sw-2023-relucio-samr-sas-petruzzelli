/*
package it.polimi.ingsw.distributed.newNetworking;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStubNew implements Server {

    private String ip;
    private int port;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Socket socket;

    public ServerStubNew(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void register(Client client, String username) throws RemoteException {

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
