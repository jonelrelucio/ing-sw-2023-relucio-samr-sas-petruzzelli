package it.polimi.ingsw.distributed.integrated;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketRunnable implements Runnable{
    private GameServer server;

    public SocketRunnable(GameServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket( 1234)){
            while(true){
                Socket socket = serverSocket.accept();
                InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
                String clientIpAddress = socketAddress.getAddress()
                        .getHostAddress();
                System.out.println("Connected socket client with ip: " + clientIpAddress);
                SocketClientHandler sch = new SocketClientHandler(socket, server);
                sch.start();
            }
        }catch(IOException e){
            throw new RuntimeException("Cannot start socket server", e);
        }
    }
}
