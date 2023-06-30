package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.integrated.messages.Message;

import java.rmi.RemoteException;

/**
 * Abstract class that represent a generic connection which can be rmi or socket
 */
public abstract class Connection {

    /**
     * username of the connected client
     */
    protected String username;

    /**
     * sends a generic string message to the client
     * @param message           message to be sent
     * @throws RemoteException  throes error when send message fails
     */
    abstract void sendMessageToClient(String message) throws RemoteException;

    /**
     * sends a generic message of type Message to the client
     * @param message           message of type Message
     * @throws RemoteException  throes error when send message fails
     */
    abstract void sendMessageToClient(Message message) throws RemoteException;

    /**
     * starts the view
     * @throws RemoteException throes error when start view fails
     */
    abstract void startView() throws RemoteException;

    /**
     * Getter for the username
     * @return  the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Updates the client with game model view and event
     * @param gameModelView     the updated game model view
     * @param eventView         the type of event
     * @throws RemoteException  throws error when fails to update client
     */
    abstract void updateClient(GameModelView gameModelView, EventView eventView) throws RemoteException;

    /**
     * Asks the maximum number of players
     * @return the max num of players
     * @throws RemoteException  throws error when fails to ask max num of players
     */
    abstract int askMaxNumOfPlayers() throws RemoteException;
}
