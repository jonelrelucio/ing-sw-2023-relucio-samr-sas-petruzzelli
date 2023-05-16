package it.polimi.ingsw.distributed.newNetworking;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements Client{

    private Server server;
    public RMIClient(Server server) throws RemoteException {
        this.server = server;
    }

    public RMIClient(int port) throws RemoteException {
        super(port);
    }

    public RMIClient(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public String receiveMessageFromServer() {
        return null;
    }
}
