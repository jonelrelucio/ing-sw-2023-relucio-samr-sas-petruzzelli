package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * The client interface tha extends the remote interface
 */
public interface Client extends Remote {

    /**
     * Remote method update called by the server
     * @param gameModelView     the updated game model
     * @param event             the event to send to the client
     * @throws RemoteException  when fails
     */
    void update(GameModelView gameModelView, EventView event) throws RemoteException;

    /**
     * Remote method called by the server
     * @param message           the client receives from the server
     * @throws RemoteException  when fails to receive from message
     */
    void receiveFromServer(String message) throws RemoteException;

    /**
     * Remote method called by the server that asks the max num of players
     * @return  the max num of players
     * @throws RemoteException  when fails to receive from the server
     */
    int askMaxNumOfPlayers() throws RemoteException;

    /**
     * starts
     * @throws RemoteException  when fails to start
     */
    void start() throws RemoteException;

    /**
     * starts the view
     * @throws RemoteException  when fails to start the view
     */
    void startView() throws RemoteException;


}
