package it.polimi.ingsw.networking;

import it.polimi.ingsw.view.PreMatchCliUI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {

    PreMatchCliUI uiView  = new PreMatchCliUI();
    @Override
    public void run() {
        uiView.run();
    }

    public ClientImpl(Server server) throws RemoteException{
        super();
        initialize(server);
    }

    public ClientImpl(Server server, int port) throws RemoteException{
        super(port);
        initialize(server);
    }

    public ClientImpl(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }


    private void initialize(Server server) throws RemoteException{
        server.register(this);
        uiView.addObserver((o, arg) ->{
            try{
                server.update(this, arg);
            }catch(RemoteException e){
                System.err.println("Unable to update the server" + e.getMessage());
            }
        });
    }

    @Override
    public void update(){

    }
}
