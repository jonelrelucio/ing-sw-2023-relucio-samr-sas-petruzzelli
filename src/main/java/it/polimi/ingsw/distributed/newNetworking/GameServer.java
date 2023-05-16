package it.polimi.ingsw.distributed.newNetworking;

import it.polimi.ingsw.distributed.newNetworking.Connection;
import it.polimi.ingsw.distributed.newNetworking.RMIConnection;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServer extends UnicastRemoteObject implements Server {
    private CircularArrayList<Connection> connections;

    public GameServer() throws RemoteException {
        connections = new CircularArrayList<>();
    }

    @Override
    public void connect(Client client) throws RemoteException{
        //Connection code
        connections.add(new RMIConnection(client));
        System.out.println("RMI connection added, connections size: "+connections.size());
        sendToAll("Messaggio inviato a questa connessione dal server");
    }

    public CircularArrayList<Connection> getConnections() {
        return connections;
    }

    public void sendToAll(String message){
        for(Connection c : connections){
            try{
                c.sendMessage(message);
            }catch(IOException e){
                System.out.println(e);
            }

        }

    }

    public void sendTo(Connection connection){

    }
}
