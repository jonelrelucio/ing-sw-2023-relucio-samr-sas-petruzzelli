package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.controller.events.GameEvent;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.model.GameModelView;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientRmi extends UnicastRemoteObject implements Client, Runnable {

    CLI view = new CLI();

    public ClientRmi(Server server) throws RemoteException {
        super();
        initialize(server);
    }

    public ClientRmi(Server server, int port) throws RemoteException {
        super(port);
        initialize(server);
    }

    public ClientRmi(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }

    private void initialize(Server server) throws RemoteException {
        server.register(this);
        view.addObserver((o, arg) -> {
            try {
                server.update(arg);
            } catch (RemoteException e) {
                System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
            }
        });
    }

    @Override
    public void update(GameModelView o, GameEvent arg) {
        view.handleViewEvent(o, arg);
    }

    @Override
    public void run() {
        view.run();
    }
}
