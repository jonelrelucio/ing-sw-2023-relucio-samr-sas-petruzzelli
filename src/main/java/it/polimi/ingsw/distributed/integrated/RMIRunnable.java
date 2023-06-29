package it.polimi.ingsw.distributed.integrated;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;

/**
 * The RMI class that implements the runnable interface
 */
public class RMIRunnable implements Runnable{
    /**
     * The port of the server
     */
    private int port;

    /**
     * The ip address of the server
     */
    private String ipAddress;

    /**
     * The server stub
     */
    private GameServer server;

    /**
     * The registry
     */
    private static Registry registry;

    /**
     * Constructor
     * @param server    the server stub
     * @param port      the port of the server
     * @param ipAddress the ip address of the server
     */
    public RMIRunnable(GameServer server, int port, String ipAddress) {
        this.server = server;
        this.port = port;
        this.ipAddress = ipAddress;
    }

    /**
     * Overrides the run method of the Runnable interface
     */
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
