package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 * The server stub that implements the server interface
 */
public class ServerStub implements Server {

    /**
     * ip address of the server stub
     */
    String ip;

    /**
     * port of the server
     */
    int port;

    /**
     * The object output stream
     */
    private ObjectOutputStream oos;

    /**
     * The object Input Stream
     */
    private ObjectInputStream ois;

    /**
     * The socket
     */
    private Socket socket;

    /**
     * constructor
     * @param ip    ip address of the connected server
     * @param port  port of the ip connected ip address of the server
     */
    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;

    }

    /**
     * the client class this method to register itself to the server
     * @param client    client that wants to register
     * @param username  the username of the client
     * @throws RemoteException  when fails to register the client
     */
    @Override
    public void register(Client client, String username) throws RemoteException {
        try {
            oos.writeObject(username);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the username chosen by the client is available
     * @param username  username of the client
     * @return          true if the username is available
     * @throws RemoteException  if fails to fails to check if username is available
     */
    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException {
        return false;
    }

    /**
     * starts
     * @throws RemoteException  if fails to start
     */
    @Override
    public void start() throws RemoteException {

    }

    /**
     * checks if the client can join the server.
     * @return  true if the client can join, false if the server has already started the game
     * @throws RemoteException  if fails to check if player can join
     */
    @Override
    public boolean canJoin() throws RemoteException {
        try{
            socket = new Socket(ip, port);
        }catch(UnknownHostException e){
            System.err.println("Unknown server");
        }catch(IOException e){
            System.err.println("Can't contact the server");
        }

        try{
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
            throw new RemoteException("Cannot create ouput stream", e);
        }

        try{
            this.ois = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            throw new RemoteException("Cannot create ouput stream", e);
        }

        try {
            boolean canJoin = (boolean) ois.readObject();
            return canJoin;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called by the client to to send a message of type Message Event to the server
     * @param messageEvent      the Message event to be sent to the server
     * @throws RemoteException  if fails to update
     */
    @Override
    public void update(MessageEvent messageEvent) throws RemoteException {
        sendObject(messageEvent);
    }

    /**
     * sends an object to the server by writing in the object output stream
     * @param object
     */
    public void sendObject(Object object){
        try{
            oos.reset();
            oos.writeObject(object);
            oos.flush();

        }catch(IOException e){
            System.err.println("Cannot send message, error "+ e);
        }
    }

    /**
     * receives an object from the server by reading the object input stream
     * @return
     */
    public Object receiveObject(){
        Object object = null;
        try{
            object = ois.readObject();
        }catch(IOException e){
            System.err.println("Cannot read object, error "+e);
        }catch(ClassNotFoundException e){
            System.err.println("Class not found, "+e);
        }
        return object;
    }
}
