package it.polimi.ingsw.distributed.integrated;

import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClientHandler extends Thread{
    /**
     * The server to which the socketConnection created by this object is added
     */
    private GameServer server;
    /**
     * The socket to be added to the list of connections of the server
     */
    private Socket socket;

    /**
     * Initialized the SocketClientHandler
     * @param socket the socket
     * @param server the server
     */
    public SocketClientHandler(Socket socket, GameServer server){
        this.socket = socket;
        this.server = server;
    }

    /**
     * Creates a new socketConnection and passes it to the gameServer to further handle it
     */
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
