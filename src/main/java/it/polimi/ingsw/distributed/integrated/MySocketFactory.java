package it.polimi.ingsw.distributed.integrated;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

/**
 * Socket Factory that extends the RMI Socket Factory
 */
public class MySocketFactory  extends RMISocketFactory implements Serializable {

    /**
     * The ip address as string
     */
    private final String ipAddress;

    MySocketFactory(String ipAddress){
        this.ipAddress = ipAddress;
    }

    /**
     * Creates the socket
     * @param host   the host name
     * @param port   the port number
     * @return       the socket
     * @throws IOException  when fails to create socket
     */
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    /**
     * Creates the server socket
     * @param port the port number
     * @return  the server socket
     * @throws IOException  when fails to create server socket
     */
    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port,0, InetAddress.getByName(ipAddress));
    }

}
