package it.polimi.ingsw.distributed.newNetworking;

import java.io.IOException;
import java.rmi.RemoteException;

public interface Connection{
    void sendMessage(String message) throws RemoteException;
}
