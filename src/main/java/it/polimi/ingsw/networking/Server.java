package it.polimi.ingsw.networking;
import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.PlayerNameEvent;
import it.polimi.ingsw.networking.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */

    void register(Client client, PlayerNameEvent e) throws RemoteException;

    /**
     * Notify the server that a client has made a choice
     * @param client  the client that generated the event
     * @param arg     the choice made by the client
     */
    //Il client è il client che ha chiamato l'update sul server
    void update(Client client, GameEvent arg) throws RemoteException;


}
