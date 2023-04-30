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
    private GameEvent event;

    private Server server;
    @Override
    public void run() {
        cliView.run();
    }

    public ClientImpl(Server server) throws RemoteException{
        super();
        cliView = new CLI();
        cliView.addObserver(this);
        this.server = server;
    }

    public ClientImpl(Server server, int port) throws RemoteException{
        super(port);

    }

    public ClientImpl(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);

    }

    public void update(MyObservable observable, PlayerNameEvent e){
        System.out.println("Il nome del giocatore, lato client: "+ e.getPlayerName());
        try{
            server.register(this, e);
        }catch(RemoteException ex){

        }

    }
    public void update(MyObservable observable, NumOfPlayersEvent e){
        System.out.println("Il numero dei giocatori è "+ e.getNumOfPlayers());
    }





    @Override
    public void update() throws RemoteException {

    }



}
