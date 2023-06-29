package it.polimi.ingsw.distributed.integrated;

import java.net.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * The main class that starts the server
 */
public class GameServerMain {
    /**
     * The main method to start the server
     * @param args  the default arguments of the main function
     */
    public static void main(String[] args) {

        try{
            GameServer server = new GameServer();
            ArrayList<String> ipAddresses = new ArrayList<>();
            ArrayList<InetAddress> ips = new ArrayList<>();

            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            int count = 0;
            for (NetworkInterface netint : Collections.list(nets)) {

                out.println("id: " + count);
                displayInterfaceInformation(netint);
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {

                    if(inetAddress instanceof Inet4Address){
                        ipAddresses.add(inetAddress.getHostAddress());
                        ips.add(inetAddress);
                    }

                }
                count++;
            }

            out.println("Choose server ip:");
            Scanner s = new Scanner(System.in);
            int id = Integer.parseInt(s.nextLine());
            InetAddress ip = ips.get(id);
            String ipAddress = ipAddresses.get(id);
            out.println("Server Ip Address: " + ipAddress);

            System.setProperty("java.rmi.server.hostname", ipAddress);
            System.setProperty("java.rmi.server.useLocalHostname", "true");

            Thread socketThread = new Thread(new SocketRunnable(server));
            Thread rmiThread = new Thread(new RMIRunnable(server, 1099, ipAddress));

            rmiThread.start();
            socketThread.start();

            try{
                rmiThread.join();
                socketThread.join();
            }catch (InterruptedException e){
                System.err.println("No connection protocol available");
            }
        }catch(RemoteException | SocketException e){

        }

    }

    /**
     * Utility method to display the available ip address of the client
     * @param netint            NetWorkInterface
     * @throws SocketException  throws exception when cant display
     */
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        out.printf("Display name: %s\n", netint.getDisplayName());
        out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if(inetAddress instanceof Inet4Address)
                out.printf("InetAddress: %s\n", inetAddress);
        }
        out.printf("\n");
    }
}
