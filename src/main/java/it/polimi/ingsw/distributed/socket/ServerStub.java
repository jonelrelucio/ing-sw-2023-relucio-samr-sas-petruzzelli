package it.polimi.ingsw.distributed.socket;

import it.polimi.ingsw.controller.events.GameEvent;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server {
    private String ip;
    private int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;

    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create output stream", e);
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream", e);
            }
        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server", e);
        }
    }

    @Override
    public void update( GameEvent event) throws RemoteException {
        try{
            oos.writeObject(event);
        }catch (IOException e){
            throw new RuntimeException("Cannot send event", e);
        }
    }

    public void receive(Client client) throws RemoteException {

    }

    public void close() throws RemoteException{
        try{
            socket.close();
        }catch (IOException e){
            throw new RemoteException("Cannot close socket", e);
        }
    }
}
