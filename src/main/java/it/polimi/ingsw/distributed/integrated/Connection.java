package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.integrated.messages.Message;

import java.rmi.RemoteException;

public abstract class Connection {

    protected String username;
    abstract void sendMessageToClient(String message) throws RemoteException;

    abstract void sendMessageToClient(Message message) throws RemoteException;
    abstract void startView() throws RemoteException;

    public String getUsername(){
        return username;
    }

    abstract void updateClient(GameModelView gameModelView, EventView eventView) throws RemoteException;


    abstract int askMaxNumOfPlayers() throws RemoteException;
}
