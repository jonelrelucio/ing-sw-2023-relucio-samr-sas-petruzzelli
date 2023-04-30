package it.polimi.ingsw;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.rmi.ServerRmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException {
        GameModel gameModel = new GameModel();
        Server server = new ServerRmi(gameModel);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("server", server);
    }
}
