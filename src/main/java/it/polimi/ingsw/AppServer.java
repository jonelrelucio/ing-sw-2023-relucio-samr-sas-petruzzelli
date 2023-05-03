package it.polimi.ingsw;

import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.rmi.ServerRmi;
import it.polimi.ingsw.model.GameModel;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServer {
    public static void main(String[] args) throws RemoteException {
        //Inizializzaizone per gestire i client RMI
        GameModel gameModel = new GameModel();
        Server server = new ServerRmi(gameModel);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("server", server);

        //Inizializzazione per gestire i client Socket

    }
}
