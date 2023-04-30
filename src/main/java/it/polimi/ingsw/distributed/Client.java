package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.model.GameModelView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(GameModelView gameModelView, GameEvent event) throws RemoteException;
}
