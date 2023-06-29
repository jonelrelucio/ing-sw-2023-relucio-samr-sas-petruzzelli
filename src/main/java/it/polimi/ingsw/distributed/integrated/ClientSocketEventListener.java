package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;

import java.rmi.RemoteException;

public class ClientSocketEventListener implements Runnable{
    private GameServer gameServer;
    private SocketConnection socketConnection;

    public ClientSocketEventListener(GameServer gameServer, SocketConnection socketConnection){
        this.gameServer= gameServer;
        this.socketConnection = socketConnection;
    }

    public void run(){
        while(true){
            MessageEvent messageEvent = (MessageEvent) socketConnection.receiveObject();
            try {
                gameServer.update(messageEvent);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
