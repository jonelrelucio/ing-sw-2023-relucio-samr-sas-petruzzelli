package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.Client;

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
            return (boolean) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public void update(GameEvent arg) throws RemoteException {

    }
}
