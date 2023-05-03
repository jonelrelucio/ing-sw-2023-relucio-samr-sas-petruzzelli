package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientRmi extends UnicastRemoteObject implements Client, Runnable {

    private String username;
    View view;

    public ClientRmi(Server server, View view) throws RemoteException {
        super();
        this.view = view;
        initialize(server);
    }

    public ClientRmi(Server server, int port, View view) throws RemoteException {
        super(port);
        this.view = view;
        initialize(server);
    }

    public ClientRmi(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, View view) throws RemoteException {
        super(port, csf, ssf);
        this.view = view;
        initialize(server);
    }

    private void initialize(Server server) throws RemoteException {

        synchronized (this ) {
            server.register(this);
            if ( server.getClients().size() == 1 ) {
                server.setMaxNumOfClients(askNumOfClients());
            }
        }


        if (view instanceof CLI viewInstance) {
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update( arg);
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

    private int askNumOfClients() {
        int numOfPlayers;
        System.out.print("Please choose number of players: ");
        do {
            numOfPlayers = view.getNumInput();
            if (numOfPlayers <= 1 || numOfPlayers >= 5 ) System.out.println("Invalid input. Only 2 to 4 Players can play the game.");
        }   while(numOfPlayers <= 1 || numOfPlayers >= 5  );
        System.out.println("You are the first player to connect...");
        return numOfPlayers;
    }

    @Override
    public void update( GameEvent event) {
        this.view.handleViewEvent( event);
    }

    @Override
    public void printFullLobby() {
        System.out.println("Lobby is full. Connect later...");
    }

    @Override
    public void run(){
        view.run();
    }


    private String askUsername() {
        System.out.print("Please choose your username: ");
        do {
            Scanner s = new Scanner(System.in);
            username = s.nextLine();
            if (username.length() < 3 || username.isBlank()) System.out.println("Invalid username, try again...");
        }   while( username.length() < 3 || username.isBlank() );
        return username;
    }

}
