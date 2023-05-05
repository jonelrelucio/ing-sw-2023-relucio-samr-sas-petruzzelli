package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote, Runnable {

    void start() throws RemoteException;

    void update( GameEvent event) throws RemoteException;

    void receiveFromServer(String message) throws RemoteException;

    int askMaxNumOfPlayers() throws RemoteException;


}
