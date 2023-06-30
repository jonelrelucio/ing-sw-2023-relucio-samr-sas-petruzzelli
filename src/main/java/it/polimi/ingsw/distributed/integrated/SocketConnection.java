package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.integrated.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection extends Connection{
    /**
     * The outputstream used by this socket connection to communicate with the client
     */
    private final ObjectOutputStream oos;
    /**
     * The inputstream used to communicate with the client
     */
    private final ObjectInputStream ois;


    /**
     * Creates a new socketConnection given the socket
     * @param socket
     */
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

    /**
     * Sends a message to the client
     * @param message           message to be sent
     */
    @Override
    public void sendMessageToClient(String message) {
        try{
            oos.writeObject(message);
            oos.flush();
            oos.reset();
        }catch(IOException e){
            System.err.println("Could not send message "+ e);
        }
    }

    /**
     * Updates the client
     * @param gameModelView     the updated game model view
     * @param eventView         the type of event
     */
    @Override
    void updateClient(GameModelView gameModelView, EventView eventView) {
        UpdateMessage updateMessage = new UpdateMessage(gameModelView, eventView);
        sendMessageToClient(updateMessage);
    }

    /**
     * Start view
     */
    @Override
    void startView()  {
        sendMessageToClient(new SimpleTextMessage(MessageType.START_VIEW_MESSAGE, ""));
    }

    /**
     * Send a message to the client to inform it what it must give the number of players
     * @return
     */
    @Override
    int askMaxNumOfPlayers() {
        sendObject(new SimpleTextMessage(MessageType.NUM_OF_PLAYERS_MESSAGE, ""));
        int numOfPlayers = (int) receiveObject();
        return numOfPlayers;
    }

    /**
     * Sends an object to the client
     * @param object The object to be sent
     */
    public void sendObject(Object object){
        try{

            oos.writeObject(object);
            oos.flush();
            oos.reset();

        }catch(IOException e){
            System.err.println("Cannot send message to client");
        }
    }

    /**
     * Receives an object from the client
     * @return the received object
     */
    public Object receiveObject(){
        try {
            return ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the username of this connection
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sends a message to the client
     * @param message           message of type Message
     */
    @Override
    public void sendMessageToClient(Message message){
        try{
            oos.writeObject(message);
            oos.flush();
            oos.reset();
        }catch(IOException e){
            System.err.println("Cannot send message to client");
        }

    }
}
