package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.integrated.Connection;
import it.polimi.ingsw.distributed.integrated.GameServer;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRunnable implements Runnable{
    private CircularArrayList<Connection> connections;
    private GameServer server;

    public RMIRunnable(GameServer server) {
        this.server = server;
    }
    @Override
    public void run(){
        try{
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("server", server);
        }catch(RemoteException e){
            System.err.println("Cannot start RMI server, aborting" + e);
        }

    }
}
