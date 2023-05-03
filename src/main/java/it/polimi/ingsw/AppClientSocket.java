package it.polimi.ingsw;

import it.polimi.ingsw.distributed.socket.ServerStub;

import java.rmi.RemoteException;

public class AppClientSocket {
    public static void main(String[] args) throws RemoteException {
        ServerStub serverStub = new ServerStub("localhost", 1099);

    }
}
