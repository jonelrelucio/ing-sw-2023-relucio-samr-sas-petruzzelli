package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;

import java.rmi.RemoteException;

/**
 * Class that listens to messages of type Message Event sent by a socket client
 */
public class ClientSocketEventListener implements Runnable{

    /**
     * Reference to the main game server
     */
    private GameServer gameServer;

    /**
     * Reference to the Socket that has sent the message
     */
    private SocketConnection socketConnection;

    /**
     * Constructor
     * @param gameServer        reference to the game server
     * @param socketConnection  reference to the socket connection
     */
    public ClientSocketEventListener(GameServer gameServer, SocketConnection socketConnection){
        this.gameServer= gameServer;
        this.socketConnection = socketConnection;
    }

    /**
     * overrides run of runnable
     */
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
