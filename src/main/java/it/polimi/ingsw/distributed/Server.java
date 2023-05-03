package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.rmi.ClientRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

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

    /**
     * Checks if the client is the first to connect the server
     * @param client            The client that wants to connect
     */
    boolean isFirst(Client client) throws  RemoteException;

    /**
     * Sets the number of maximum clients that can successfully connect to the server
     * @param maxNumOfClients   the maximum number of clients that can succesfully connect
     */
    void setMaxNumOfClients(int maxNumOfClients) throws RemoteException;

    /**
     * Checks if the client can connect to the server
     * @param client    Client that wants to connect to the server
     * @return          true if the client can connect
     */
    boolean canConnect(Client client) throws RemoteException;


    ArrayList<Client> getClients() throws RemoteException;
}
