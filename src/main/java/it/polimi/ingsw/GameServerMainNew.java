/*
package it.polimi.ingsw;

import it.polimi.ingsw.distributed.integrated.GameServer;
import it.polimi.ingsw.distributed.newNetworking.GameServerNew;
import it.polimi.ingsw.distributed.integrated.RMIRunnable;
import it.polimi.ingsw.distributed.integrated.SocketRunnable;

import java.rmi.RemoteException;

public class GameServerMainNew {
    public static void main(String[] args) {

        try{
            GameServer server = new GameServer();
            //start the RMI thread
            Thread socketThread = new Thread(new SocketRunnable(server));
            Thread rmiThread = new Thread(new RMIRunnable(server));

            rmiThread.start();

            //start the socket thread
            socketThread.start();

            try{
                rmiThread.join();
                socketThread.join();
            }catch (InterruptedException e){
                System.err.println("No connection protocol available");
            }
        }catch(RemoteException e){

        }



    }
}
*/
