package it.polimi.ingsw.distributed.integrated;

import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClientHandler extends Thread{
    private GameServer server;
    private Socket socket;

    public SocketClientHandler(Socket socket, GameServer server){
        this.socket = socket;
        this.server = server;
    }
    @Override
    public void run() {
        SocketConnection socketConnection = new SocketConnection(socket);
        try {
            if(server.canJoin()){
                server.getConnections().add(socketConnection);
                socketConnection.sendObject(true);
                String connectionUsername = (String) socketConnection.receiveObject();
                socketConnection.setUsername(connectionUsername);
                server.manageConnection(socketConnection);
            }else{

            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
