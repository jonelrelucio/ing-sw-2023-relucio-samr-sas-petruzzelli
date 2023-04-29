package it.polimi.ingsw;

import it.polimi.ingsw.networking.ClientImpl;
import it.polimi.ingsw.networking.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppClientRMI {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        Server server =  (Server) registry.lookup("server");
        ClientImpl client = new ClientImpl(server);
        client.run();
    }
}
