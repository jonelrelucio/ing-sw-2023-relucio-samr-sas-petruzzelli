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

public class ClientRmi extends UnicastRemoteObject implements Client, Runnable {

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
        server.register(this);
        view.isConnected(true);
        if (view instanceof CLI ) {
            CLI viewInstance = (CLI) view;
            viewInstance.addObserver((o, arg) -> {
                try {
                    server.update( arg);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            });
            view.isConnected(server.getNumOfClients() == 1);

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

    @Override
    public void update( GameEvent event) {
        this.view.handleViewEvent( event);
    }

    @Override
    public void run(){
        view.run();
    }

}
