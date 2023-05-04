package it.polimi.ingsw.distributed;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    void register(Client client, String username) throws RemoteException;

    boolean isUsernameAvailable(String username) throws RemoteException;

    void start() throws RemoteException;

    boolean canJoin() throws RemoteException;
}
