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

public class ServerStub implements Server {
    /**
     * The ip of the server
     */
    String ip;
    /**
     * The port used by the socket communication
     */
    int port;
    /**
     * The outputstream used to send data to the server
     */
    private ObjectOutputStream oos;
    /**
     * The inputstream used to receive data from the server
     */
    private ObjectInputStream ois;
    /**
     * The socket used to communicate with the server
     */
    private Socket socket;

    /**
     * Sets the ip and the port
     * @param ip
     * @param port
     */
    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;

    }

    /**
     *
     * @param client
     * @param username
     * @throws RemoteException
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
     * Check if the username is available
     * @param username
     * @return the availability of the name
     * @throws RemoteException
     */
    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException {
        return false;
    }

    /**
     * start
     * @throws RemoteException
     */
    @Override
    public void start() throws RemoteException {

    }

    /**
     * Contacts the server to ask if the client can join the match
     * @return true if the client can join the match, false otherwise
     * @throws RemoteException
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
     * Sends a messageEvent to the server
     * @param messageEvent
     * @throws RemoteException
     */
    @Override
    public void update(MessageEvent messageEvent) throws RemoteException {
        sendObject(messageEvent);
    }

    /**
     * Sends a generic object to the server via the Outputstream
     * @param object The object being sent
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
     * Receives an object sent by the server
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
