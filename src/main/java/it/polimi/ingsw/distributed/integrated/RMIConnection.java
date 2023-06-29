package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.integrated.messages.Message;
import it.polimi.ingsw.distributed.integrated.messages.SimpleTextMessage;

import java.rmi.RemoteException;

/**
 * Class that extends Connection of type RMI
 */
public class RMIConnection extends Connection{

    /**
     * The reference to the client
     */
    private Client client;

    /**
     * Cons
     * @param client
     * @param username
     */
    public RMIConnection(Client client, String username){
        this.client = client;
        this.username = username;
    }

    public Client getClient(){
        return client;
    }

    @Override
    public void sendMessageToClient(String message) throws RemoteException {
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
    void updateClient(GameModelView gameModelView, EventView eventView) throws RemoteException {
        client.update(gameModelView, eventView);
    }

    @Override
    public void sendMessageToClient(Message message){

        if(message instanceof SimpleTextMessage){
            try{
                client.receiveFromServer(((SimpleTextMessage) message).getMessage());
            }catch(RemoteException e){
                System.err.println("Cannot send message to client, "+e);
            }

        }
    }

}
