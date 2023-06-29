package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.guiController.ViewGui;
import it.polimi.ingsw.distributed.Client;
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
                    server.register(this, username);
                }catch(RemoteException e){
                    throw new RuntimeException(e);
                }
                if(!waitForPlayers()) return;
                addObserver();
                listenGameEvents();
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
            ViewGui viewInstance = (ViewGui) view;
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update(arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            });
        }
    }

    private boolean waitForPlayers(){

        while(true){
            SimpleTextMessage message = (SimpleTextMessage) server.receiveObject();
            if(message.getMessageType() == MessageType.START_GAME_MESSAGE) return true;
            if(message.getMessageType() == MessageType.FIRST_PLAYER_MESSAGE){
                SimpleTextMessage numOfPlayersMessage = (SimpleTextMessage) server.receiveObject();
                if(numOfPlayersMessage.getMessageType() == MessageType.NUM_OF_PLAYERS_MESSAGE){
                    new Thread( () -> {
                        try{
                            int numOfPlayers = askMaxNumOfPlayers();
                            server.sendObject(numOfPlayers);
                        }catch(RemoteException e){
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
            }
            if(message.getMessageType() == MessageType.REMAINING_PLAYERS_MESSAGE) view.printMessage(message.getMessage());
            if(message.getMessageType() == MessageType.DEFAULT_MESSAGE) view.printMessage(message.getMessage());
            if(message.getMessageType() == MessageType.KICK_MESSAGE) {
                view.printMessage(message.getMessage());
                return false;
            }
        }
    }

    private void listenGameEvents(){
        while(true){
            UpdateMessage updateMessage = (UpdateMessage) server.receiveObject();
            try{
                update(updateMessage.getGameModelView(), updateMessage.getEventView());
            }catch(RemoteException e){
                System.err.println("Cannot update client "+e);
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
        view.setThisUsername(username);
    }


    @Override
    public void update(GameModelView gameModelView, EventView event) throws RemoteException {
        if (event != NEW_TURN) {
            new Thread(() -> {
                view.ViewEventHandler(gameModelView, event);
            }).start();
        }
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
