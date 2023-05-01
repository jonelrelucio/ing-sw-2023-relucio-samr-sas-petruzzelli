package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */

    void register(Client client) throws RemoteException;

    /**
     * Notify the server that a client has sent an event
     * @param event     the game event sent by the client
     */
    //Il client è il client che ha chiamato l'update sul server
    void update( GameEvent event) throws RemoteException;


    int getNumOfClients() throws RemoteException;
}
