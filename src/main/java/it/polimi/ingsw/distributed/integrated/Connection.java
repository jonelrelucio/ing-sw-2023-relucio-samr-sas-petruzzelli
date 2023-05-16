package it.polimi.ingsw.distributed.integrated;

import java.rmi.RemoteException;

public abstract class Connection {

    protected String username;
    abstract void sendMessageToClient(String message) throws RemoteException;
    abstract void startView() throws RemoteException;

    public String getUsername(){
        return username;
    }

}
