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

    /**
     * Getter for retrieving the client
     * @return  the client
     */
    public Client getClient(){
        return client;
    }

    /**
     * Remote method used by the server to send a message to the client
     * @param message           message to be sent, that is received by the client
     * @throws RemoteException  if server fails to send the message to client
     */
    @Override
    public void sendMessageToClient(String message) throws RemoteException {
        client.receiveFromServer(message);
    }

    /**
     * server starts the view of the client
     */
    @Override
    void startView() {
        try{
            client.startView();
        }catch(RemoteException e){
            System.err.println("Cannot start view" + e);
        }
    }

    /**
     * Asks the max number of player remotely by the server
     * @return  the max number of players
     * @throws RemoteException  if fails to ask max num of players
     */
    @Override
    int askMaxNumOfPlayers() throws RemoteException {
        return client.askMaxNumOfPlayers();
    }

    /**
     * Remote method used by the server to update this client
     * @param gameModelView     the updated game model view
     * @param eventView         the type of event
     * @throws RemoteException  when fails to update client
     */
    @Override
    void updateClient(GameModelView gameModelView, EventView eventView) throws RemoteException {
        client.update(gameModelView, eventView);
    }

    /**
     * Remote method used by the server to send a message to this client
     * @param message           message of type Message
     */
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
