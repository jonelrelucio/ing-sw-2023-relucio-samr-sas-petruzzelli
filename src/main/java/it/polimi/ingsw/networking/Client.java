package it.polimi.ingsw.networking;

import java.rmi.RemoteException;

public interface Client {
    void update() throws RemoteException;
}
