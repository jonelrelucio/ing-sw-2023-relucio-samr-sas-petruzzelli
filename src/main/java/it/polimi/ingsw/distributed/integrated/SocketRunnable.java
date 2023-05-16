package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.integrated.GameServer;
import it.polimi.ingsw.distributed.integrated.SocketConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketRunnable implements Runnable{
    private GameServer server;

    public SocketRunnable(GameServer server){
        this.server = server;
    }

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while(true){
                Socket socket = serverSocket.accept();
                /*
                //spawn a thread to handle the new connected socket
                new Thread(){
                    @Override
                    public void run() {
                        connections.add(new SocketConnection(socket));
                        System.out.println("Socket connection added, connections size: "+connections.size());

                    }
                }.start();

                 */
                server.getConnections().add(new SocketConnection(socket));
                System.out.println("Socket connection added, connections size: "+server.getConnections().size());
                //server.sendToAll("Messaggio inviato a questa connessione dal Server");
            }
        }catch(IOException e){
            throw new RuntimeException("Cannot start socket server", e);
        }
    }
}
