package it.polimi.ingsw.distributed.newNetworking;

import java.rmi.RemoteException;

public class RMIConnection implements Connection{
    private Client client;

    public RMIConnection(Client client){
        this.client = client;
    }

    @Override
    public void sendMessage(String message) {
        try{
            client.receiveMessageFromServer();
        }catch(RemoteException e){
            System.err.println("Cannot send message to RMI client" + e);
        }

    }
}
