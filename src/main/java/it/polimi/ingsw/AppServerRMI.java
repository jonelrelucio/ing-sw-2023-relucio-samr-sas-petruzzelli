package it.polimi.ingsw;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.rmi.ServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException {
        GameModel gameModel = new GameModel();
        Server server = new ServerImpl(gameModel);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("server", server);
    }
}
