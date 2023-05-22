package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.RemoteException;

public abstract class Connection {

    protected String username;
    abstract void sendMessageToClient(String message) throws RemoteException;
    abstract void startView() throws RemoteException;

    public String getUsername(){
        return username;
    }

    abstract void updateClient(GameEvent event) throws RemoteException;

    abstract int askMaxNumOfPlayers() throws RemoteException;

}
