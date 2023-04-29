package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.PlayerNameEvent;
import it.polimi.ingsw.util.Observable;
import it.polimi.ingsw.view.CLI;
import org.w3c.dom.events.Event;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {

    //CLI cliView = new CLI();
    private CLI cliView;
    @Override
    public void run() {
        cliView.run();
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
    }

    public void update(Observable<Event> observable, PlayerNameEvent e){
        System.out.println("Ho osservato un evento PlayerNameEvent");
    }

    @Override
    public void update() throws RemoteException {

    }


    @Override
        public <GameEventType extends GameEvent> void update(MyObservable observable, GameEventType e) {

    }
}
