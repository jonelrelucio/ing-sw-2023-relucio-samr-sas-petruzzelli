package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;

import java.rmi.RemoteException;

public class RMIConnection extends Connection{
    private Client client;//Provare a fare RMIClient

    private String username;//TODO: trovare un altro modo per visualizzare il username, non in connection

    public RMIConnection(Client client, String username){
        this.client = client;
        this.username = username;
    }

    public Client getClient(){
        return client;
    }

    @Override
    public void sendMessageToClient(String message) throws RemoteException {
        //TODO: implementare il metodo
        client.receiveFromServer(message);
    }

    @Override
    void startView() {
        try{
            client.startView();
        }catch(RemoteException e){
            System.err.println("Cannot start view" + e);
        }

    }
}
