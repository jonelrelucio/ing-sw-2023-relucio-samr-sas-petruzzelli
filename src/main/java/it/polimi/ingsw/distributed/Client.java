package it.polimi.ingsw.distributed;

import java.rmi.RemoteException;

public interface Client {
    void update() throws RemoteException;
}
