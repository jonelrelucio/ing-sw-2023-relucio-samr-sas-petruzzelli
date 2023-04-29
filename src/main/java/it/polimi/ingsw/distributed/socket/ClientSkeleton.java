package it.polimi.ingsw.distributed.socket;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public ClientSkeleton (Socket socket) throws RemoteException{
        try{
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            throw new RemoteException("Cannot create output stream", e);
        }
        try{
            this.ois = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            throw new RemoteException("Cannot create input stream", e);
        }
    }
    @Override
    public void update() throws RemoteException {

    }

    public void receive(Server server) throws RemoteException{

    }
}
