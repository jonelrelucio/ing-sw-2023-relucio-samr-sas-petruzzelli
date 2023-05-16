package it.polimi.ingsw.distributed.newNetworking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void connect(Client client) throws RemoteException;
}
