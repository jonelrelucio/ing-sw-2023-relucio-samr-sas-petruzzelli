package it.polimi.ingsw.distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Serv extends Remote {
    void sayHi() throws RemoteException;
}
