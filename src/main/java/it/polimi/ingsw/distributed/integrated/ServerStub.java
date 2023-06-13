package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerStub implements Server {
    String ip;
    int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;

    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;

    }



    /*public void connect(Client client) throws RemoteException {
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
    }*/

    @Override
    public void register(Client client, String username) throws RemoteException {
        try {
            oos.writeObject(username);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isUsernameAvailable(String username) throws RemoteException {
        return false;
    }

    @Override
    public void start() throws RemoteException {

    }

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

    @Override
    public void update(MessageEvent messageEvent) throws RemoteException {
        //TODO: sostituisce update(GameEvent event)

    }

    /*
    @Override
    public void update(GameEvent arg) throws RemoteException {

    }
    */

    public void sendObject(Object object){
        try{

            oos.writeObject(object);
            oos.flush();
        }catch(IOException e){
            System.err.println("Cannot send message, error "+ e);
        }
    }

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
