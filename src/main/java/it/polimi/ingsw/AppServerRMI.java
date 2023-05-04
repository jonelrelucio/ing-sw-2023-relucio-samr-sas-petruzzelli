package it.polimi.ingsw;

import it.polimi.ingsw.distributed.newRmi.ServerImpl;
import it.polimi.ingsw.distributed.Server;

import java.rmi.RemoteException;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();
        server.start();
    }
}
