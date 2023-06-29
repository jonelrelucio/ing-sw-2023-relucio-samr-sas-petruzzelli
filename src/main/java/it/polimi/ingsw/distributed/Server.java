package it.polimi.ingsw.distributed;


import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Server interface that extends the remote interface
 */
public interface Server extends Remote {

    /**
     * Remote method called by the client to register itself to the server
     * @param client    the client that wants to get registered
     * @param username  the username of the username
     * @throws RemoteException  when fails to register
     */
    void register(Client client, String username) throws RemoteException;

    /**
     * Remote method called by the client to check if the chosen username is available
     * @param username  the username to be checked
     * @return  true if the username is available
     * @throws RemoteException
     */
    boolean isUsernameAvailable(String username) throws RemoteException;

    /**
     * starts
     * @throws RemoteException if fails to start
     */
    void start() throws RemoteException;

    /**
     * Method called by the client if it can still join the game
     * @return  true if can join, false if the game has already started
     * @throws RemoteException
     */
    boolean canJoin() throws RemoteException;

    /**
     * Method called by the client to update the game model by sending a message of type Message Event
     * @param messageEvent  the message sent by the client
     * @throws RemoteException  if fails to update
     */
    void update( MessageEvent messageEvent) throws RemoteException;
}
