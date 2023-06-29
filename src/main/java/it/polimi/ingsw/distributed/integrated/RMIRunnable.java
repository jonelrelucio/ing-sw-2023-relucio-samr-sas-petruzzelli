package it.polimi.ingsw.distributed.integrated;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;

public class RMIRunnable implements Runnable{
    private int port;
    private String ipAddress;
    private GameServer server;
    private static Registry registry;

    public RMIRunnable(GameServer server, int port, String ipAddress) {
        this.server = server;
        this.port = port;
        this.ipAddress = ipAddress;
    }
    @Override
    public void run(){
        try{
            RMISocketFactory.setSocketFactory(new MySocketFactory(ipAddress));
            registry = LocateRegistry.createRegistry(port);
            registry.rebind("server", server);
        }catch(RemoteException e){
            System.err.println("Cannot start RMI server, aborting" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
