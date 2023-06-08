package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.integrated.messages.ClientUpdateMessage;
import it.polimi.ingsw.distributed.integrated.messages.Message;
import it.polimi.ingsw.distributed.integrated.messages.MessageType;
import it.polimi.ingsw.distributed.integrated.messages.SimpleTextMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketConnection extends Connection{
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private Socket socket;

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
        sendMessageToClient(new ClientUpdateMessage(event));
    }

    @Override
    void startView() throws RemoteException {
        sendMessageToClient(new SimpleTextMessage(MessageType.START_VIEW_MESSAGE, ""));
    }

    @Override
    int askMaxNumOfPlayers() throws RemoteException {
        sendObject(new SimpleTextMessage(MessageType.NUM_OF_PLAYERS_MESSAGE, ""));
        int numOfPlayers = (int) receiveObject();
        return numOfPlayers;
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

    @Override
    public void sendMessageToClient(Message message){
        try{
            oos.writeObject(message);
            oos.flush();
        }catch(IOException e){
            System.err.println("Cannot send message to client");
        }

    }
}
