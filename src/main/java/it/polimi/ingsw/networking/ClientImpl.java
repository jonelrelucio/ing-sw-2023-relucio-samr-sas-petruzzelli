package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.NumOfPlayersEvent;
import it.polimi.ingsw.events.PlayerNameEvent;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.MyObservable;
import it.polimi.ingsw.util.MyObserver;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.PreMatchCliUI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable, MyObserver {

    //CLI cliView = new CLI();
    private CLI cliView;
    @Override
    public void run() {
        cliView.run();
    }

    public ClientImpl(Server server) throws RemoteException{
        super();
        cliView = new CLI();
        cliView.addObserver(this);
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

    }





    public void update(MyObservable observable, PlayerNameEvent e){
        System.out.println("Ho osservato un evento PlayerNameEvent");

    }





    @Override
    public void update() throws RemoteException {

    }


    @Override
    public <GameEventType extends GameEvent> void update(MyObservable observable, GameEventType e) {

    }
}
