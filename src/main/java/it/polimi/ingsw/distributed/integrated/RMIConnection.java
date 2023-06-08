package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.integrated.messages.Message;

import java.rmi.RemoteException;

public class RMIConnection extends Connection{
    private Client client;//Provare a fare RMIClient

    //TODO: trovare un altro modo per visualizzare l'username

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

    @Override
    int askMaxNumOfPlayers() throws RemoteException {
        return client.askMaxNumOfPlayers();
    }

    @Override
    void updateClient(GameEvent event) throws RemoteException {
        //spawnare un tnread
        client.update(event);
    }

    @Override
    public void sendMessageToClient(Message message){

    }
}
