package it.polimi.ingsw;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.distributed.newRmi.ClientImpl;

import java.rmi.RemoteException;

public class AppClientRMI {
    public static void run(View view) throws RemoteException {
        ClientImpl client = new ClientImpl(view);
        client.run();
    }
}
