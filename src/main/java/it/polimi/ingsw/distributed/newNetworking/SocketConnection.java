package it.polimi.ingsw.distributed.newNetworking;

import it.polimi.ingsw.distributed.newNetworking.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketConnection implements Connection {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private Socket socket;

    public SocketConnection(Socket socket){
        try{
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
            throw new RuntimeException("Cannot create output stream", e);
        }

        try{
            this.ois = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            throw new RuntimeException("Cannot create output stream", e);
        }

    }
    @Override
    public void sendMessage(String message) throws RemoteException {
        try{
            oos.writeObject(message);
            oos.flush();
        }catch(IOException e){
            throw new RemoteException("Cannot send message to client");
        }

    }
}
