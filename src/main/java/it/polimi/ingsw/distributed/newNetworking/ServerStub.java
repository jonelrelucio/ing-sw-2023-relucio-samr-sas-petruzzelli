package it.polimi.ingsw.distributed.newNetworking;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server{
    String ip;
    int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;

    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;

    }

    @Override
    public void connect(Client client) throws RemoteException {
        try{
            this.socket = new Socket(ip, port);
            try{
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            }catch(IOException e){
                throw new RemoteException("Cannot create output stream", e);
            }

            try{
                this.ois = new ObjectInputStream(socket.getInputStream());
            }catch(IOException e){
                throw new RemoteException("Cannot crate input stream", e);
            }
        }catch(IOException e){
            throw new RemoteException("Unable to connect to the server via socket", e);
        }
    }
}
