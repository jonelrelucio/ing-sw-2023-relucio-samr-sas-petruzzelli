package it.polimi.ingsw;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.rmi.ClientRmi;
import it.polimi.ingsw.distributed.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppClientRMI {
    public static void run(View view) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        Server server =  (Server) registry.lookup("server");
        ClientRmi client = new ClientRmi(server, view);
    }
}
