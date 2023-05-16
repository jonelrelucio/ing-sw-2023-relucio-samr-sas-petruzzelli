package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.newNetworking.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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


    public void connect(Client client) throws RemoteException {
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
    }

    @Override
    public void register(it.polimi.ingsw.distributed.Client client, String username) throws RemoteException {

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
        return false;
    }

    @Override
    public void update(GameEvent arg) throws RemoteException {

    }
}
