package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.RemoteException;

public class SocketClient implements Client {
    @Override
    public void update(GameEvent event) throws RemoteException {

    }

    @Override
    public void receiveFromServer(String message) throws RemoteException {

    }

    @Override
    public int askMaxNumOfPlayers() throws RemoteException {
        return 0;
    }

    @Override
    public void startView() throws RemoteException {

    }
}
