package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.GameEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketConnection extends Connection{
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private Socket socket;
    private String username;

    public SocketConnection(Socket socket){
        //this.username = username;
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
    public void sendMessageToClient(String message) throws RemoteException {

    }

    @Override
    public void updateClient(GameEvent event) throws RemoteException {

    }

    @Override
    void startView() throws RemoteException {

    }

    @Override
    void askMaxNumOfPlayers() throws RemoteException {

    }
}
