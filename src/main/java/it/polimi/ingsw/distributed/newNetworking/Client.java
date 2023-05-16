package it.polimi.ingsw.distributed.newNetworking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client  extends Remote {
    String receiveMessageFromServer() throws RemoteException;
}
