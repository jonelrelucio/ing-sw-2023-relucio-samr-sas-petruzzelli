package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.rmi.RemoteException;

public class ClientSocketEventListener implements Runnable{
    private GameServer gameServer;
    private SocketConnection socketConnection;

    public ClientSocketEventListener(GameServer gameServer, SocketConnection socketConnection){
        this.gameServer= gameServer;
        this.socketConnection = socketConnection;
    }

    public void run(){
        //TODO: fare un altra condizione di uscita dal ciclo
        while(true){
            MessageEvent messageEvent = (MessageEvent) socketConnection.receiveObject();
            try {
                gameServer.update(messageEvent);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            //gameServer.updateClients();
        }
        //update the model based on the type of the message [server.update(messageEvent)]
        //updateAllTheClients

    }
}
