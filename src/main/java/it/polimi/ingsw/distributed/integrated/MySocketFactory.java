package it.polimi.ingsw.distributed.integrated;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class MySocketFactory  extends RMISocketFactory implements Serializable {

    private final String ipAddress;

    MySocketFactory(String ipAddress){
        this.ipAddress = ipAddress;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port,0, InetAddress.getByName(ipAddress));
    }

}
