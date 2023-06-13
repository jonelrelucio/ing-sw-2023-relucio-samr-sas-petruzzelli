package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.distributed.integrated.messages.*;

import java.rmi.RemoteException;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.NEW_TURN;

public class SocketClient implements Client, Runnable{

    private View view;
    private ServerStub server;
    private String username;
    private boolean connectionError = false;

    public SocketClient(ServerStub serverStub, View view){
        this.server = serverStub;
        this.view = view;
    }

    @Override
    public void run() {
        /*
        try {
            if (!server.canJoin()) System.out.println("The game has already started. Come back later.");
            else if (!connectionError) {
                try {
                    askUsername();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                try {
                    server.register(this, username);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                addObserver();
            }
            else System.out.println("Can't connect to server");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

         */
        //il server.canJoin fa un socket (in questo caso il server è il serverStub).
        try {
            if(!server.canJoin()){
                System.out.println("The game has already started. Come back later");
            }else if (!connectionError){
                try{
                    askUsername();
                }catch (RemoteException e){
                    throw new RuntimeException(e);
                }
                try{
                    server.register(this, username);//il client non mi serve, ma lo faccio per avere un metodo comune, per poi mettere tutto in una classe padre
                }catch(RemoteException e){
                    throw new RuntimeException(e);
                }

                //Non so cosa va prima
                waitForPlayers();
                addObserver();



            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void addObserver() {
        if (view instanceof CLI viewInstance) {
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            });
        } else {
            GUI viewInstance = (GUI) view;
            //TODO: View is does not extend Observable yet
//            viewInstance.addObserver((o, arg) -> {
//                try {
//                    server.update(arg);
//                } catch (RemoteException e) {
//                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
//                }
//            });
        }
    }

    private void waitForPlayers(){
        /*
        Message message = (Message) server.receiveObject();
        //cambiare la condizione nel while
        while (message.getMessageType() == MessageType.FIRST_PLAYER_MESSAGE){
            message = (Message) server.receiveObject();
            if(message.getMessageType() == MessageType.NUM_OF_PLAYERS_MESSAGE){
                try {
                    int numOfPlayers = askMaxNumOfPlayers();
                    server.sendObject(numOfPlayers);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

                //ricevuta stringa che dice quanti rimangono

            }
        }

         */
        /*
        SimpleTextMessage message;
        do{
            message = (SimpleTextMessage) server.receiveObject();
            if(message.getMessageType() == MessageType.FIRST_PLAYER_MESSAGE){
                SimpleTextMessage numOfPlayersMessage = (SimpleTextMessage) server.receiveObject();  //SE NON VA COSì CAMBIARE DA numOfPlayersMessage a message dichiarato sopra
                if(numOfPlayersMessage.getMessageType() == MessageType.NUM_OF_PLAYERS_MESSAGE){
                    try{
                        int numOfPlayers = askMaxNumOfPlayers();
                        server.sendObject(numOfPlayers);
                    }catch(RemoteException e){
                        throw new RuntimeException(e);
                    }
                }
                //waiting for x to set the number of players
                SimpleTextMessage waitToSetNumOfPlayers = (SimpleTextMessage) server.receiveObject();
                view.printMessage(waitToSetNumOfPlayers.getMessage());
                //inviare alla cli per la stampa
                //enter maximum number of players
                SimpleTextMessage enterMaximumNumOfPlayers = (SimpleTextMessage) server.receiveObject();    //forse è da togliere
                view.printMessage(enterMaximumNumOfPlayers.getMessage());


            }
            if(message.getMessageType() == MessageType.REMAINING_PLAYERS_MESSAGE){
                view.printMessage(message.getMessage());
            }
        }while (message.getMessageType() != MessageType.START_GAME_MESSAGE);*/

        SimpleTextMessage message = (SimpleTextMessage) server.receiveObject();
        while(message.getMessageType() != MessageType.START_GAME_MESSAGE){
            if(message.getMessageType() == MessageType.FIRST_PLAYER_MESSAGE){
                SimpleTextMessage numOfPlayersMessage = (SimpleTextMessage) server.receiveObject();  //SE NON VA COSì CAMBIARE DA numOfPlayersMessage a message dichiarato sopra
                if(numOfPlayersMessage.getMessageType() == MessageType.NUM_OF_PLAYERS_MESSAGE){
                    try{
                        int numOfPlayers = askMaxNumOfPlayers();
                        server.sendObject(numOfPlayers);
                    }catch(RemoteException e){
                        throw new RuntimeException(e);
                    }
                }
                //waiting for x to set the number of players
                SimpleTextMessage waitToSetNumOfPlayers = (SimpleTextMessage) server.receiveObject();
                view.printMessage(waitToSetNumOfPlayers.getMessage());
                //inviare alla cli per la stampa
                //enter maximum number of players
                SimpleTextMessage enterMaximumNumOfPlayers = (SimpleTextMessage) server.receiveObject();    //forse è da togliere
                view.printMessage(enterMaximumNumOfPlayers.getMessage());
                if(enterMaximumNumOfPlayers.getMessageType() == MessageType.START_GAME_MESSAGE){
                    break;
                }

            }
            if(message.getMessageType() == MessageType.REMAINING_PLAYERS_MESSAGE){
                view.printMessage(message.getMessage());
            }





            message = (SimpleTextMessage) server.receiveObject();
        }


        //updateClientMessage
        //ClientUpdateMessage clientUpdateMessage = (ClientUpdateMessage)server.receiveObject();
        UpdateMessage updateMessage = (UpdateMessage) server.receiveObject();
        try{
            //update(clientUpdateMessage.getGameEvent());
            update(updateMessage.getGameModelView(), updateMessage.getEventView());

        }catch(RemoteException e){
            System.err.println("Cannot update client, "+e);
        }

        SimpleTextMessage startViewMessage = (SimpleTextMessage) server.receiveObject();
        if(startViewMessage.getMessageType() == MessageType.START_VIEW_MESSAGE){
            try{
                startView();
            }catch(RemoteException e){
                System.err.println("Cannot start view");
            }
        }


    }

    private void askUsername() throws RemoteException{
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printMessage("The username is not Available. Try Again.");
        } while(server.isUsernameAvailable(username));
        this.username = username;
    }

    /*
    @Override
    public void update(GameEvent event) throws RemoteException {
        if (! (event instanceof GameModelView gameModelView)) throw new RuntimeException("Game Event is not instance of GameModelView");
        view.update(gameModelView);
    }

     */

    @Override
    public void update(GameModelView gameModelView, EventView event) throws RemoteException {
        //serve spawnare un nuovo thread per il socket?
        if (event != NEW_TURN) view.ViewEventHandler(gameModelView, event);
        else {
            new Thread( () -> {
                view.newTurn(gameModelView);
            }).start();
        }
    }

    @Override
    public void receiveFromServer(String message) throws RemoteException {

    }

    @Override
    public int askMaxNumOfPlayers() throws RemoteException {
        return view.askMaxNumOfPlayers();
    }

    @Override
    public void start() throws RemoteException {

    }

    @Override
    public void startView() throws RemoteException {
        view.run();
    }
}
