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
        try{
            oos.writeObject(message);
            oos.flush();

        }catch(IOException e){
            System.err.println("Could not send message "+ e);
        }
    }

    @Override
    public void updateClient(GameEvent event) throws RemoteException {

    }

    @Override
    void startView() throws RemoteException {

    }

    @Override
    int askMaxNumOfPlayers() throws RemoteException {
        return 0;//TODO: implement
    }

    public void sendObject(Object object){
        try{

            oos.writeObject(object);
            oos.flush();
        }catch(IOException e){
            System.err.println("Cannot send message to client");
        }
    }

    public Object receiveObject(){
        try {
            return ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
