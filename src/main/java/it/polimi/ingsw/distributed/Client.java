package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {



    void update(GameModelView gameModelView, EventView event) throws RemoteException;

    void receiveFromServer(String message) throws RemoteException;

    int askMaxNumOfPlayers() throws RemoteException;

    void start() throws RemoteException;

    void startView() throws RemoteException;


}
