package it.polimi.ingsw.distributed;

import it.polimi.ingsw.distributed.events.GameEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client{

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public ClientSkeleton(Socket socket) throws RemoteException{
        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
            throw new RemoteException("Cannot create outputstream ", e);
        }

        try{
            ois = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            throw new RemoteException("Cannot create inputstream ", e);
        }
    }

    @Override
    public void update(GameEvent event) throws RemoteException {

    }

    @Override
    public void receiveFromServer(String message) throws RemoteException {

    }

    @Override
    public int askMaxNumOfPlayers() throws RemoteException {
        return 0;
    }

    @Override
    public void startView() throws RemoteException {

    }
}
