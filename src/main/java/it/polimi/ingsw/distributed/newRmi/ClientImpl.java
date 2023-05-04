package it.polimi.ingsw.distributed.newRmi;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.events.GameEvent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable{

    private final View view;
    private String username;
    private Server server;
    private boolean connectionError = false;

    public ClientImpl(View view) throws RemoteException {
        super();
        this.view = view;
        run();
    }

    public void start() {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            connectionError = true;
        }
        try {
            assert registry != null;
            server =  (Server) registry.lookup("server");
        } catch (NotBoundException | RemoteException e) {
            connectionError = true;
        }
        if (!connectionError) {
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
        }
        else System.out.println("Can't connect to server");
    }


    private void askUsername() throws RemoteException {
        String username;
        do {
            username = view.askUsername().toLowerCase();
            if (server.isUsernameAvailable(username)) view.printUsernameNotAvailable();
        } while(server.isUsernameAvailable(username));
        this.username = username;
    }


    @Override
    public void update(GameEvent event) throws RemoteException {

    }

    @Override
    public void receiveFromServer(String message) {
        view.printMessageFromServer(message);
    }

    @Override
    public int askMaxNumOfPlayers() {
        return view.askMaxNumOfPlayers();
    }


    @Override
    public void run() {
        view.run();
    }
}
