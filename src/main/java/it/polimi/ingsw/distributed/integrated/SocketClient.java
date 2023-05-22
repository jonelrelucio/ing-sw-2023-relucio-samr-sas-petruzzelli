package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.rmi.RemoteException;

public class SocketClient implements Client, Runnable{

    private View view;
    private Server server;
    private String username;
    private boolean connectionError = false;

    public SocketClient(Server serverStub, View view){
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

    private void askUsername() throws RemoteException{
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printMessage("The username is not Available. Try Again.");
        } while(server.isUsernameAvailable(username));
        this.username = username;
    }

    @Override
    public void update(GameEvent event) throws RemoteException {
        if (! (event instanceof GameModelView gameModelView)) throw new RuntimeException("Game Event is not instance of GameModelView");
        view.update(gameModelView);
    }

    @Override
    public void receiveFromServer(String message) throws RemoteException {

    }

    @Override
    public int askMaxNumOfPlayers() throws RemoteException {
        return view.askMaxNumOfPlayers();
    }

    @Override
    public void startView() throws RemoteException {
        view.run();
    }
}
