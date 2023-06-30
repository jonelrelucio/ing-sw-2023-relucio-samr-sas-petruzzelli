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
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if(inetAddress instanceof Inet4Address){
                        ipAddresses.add(inetAddress.getHostAddress());
                        ips.add(inetAddress);
                        out.println("id: " + count);
                        out.printf("Display name: %s\n", netint.getDisplayName());
                        out.printf("Name: %s\n", netint.getName());
                        out.printf("InetAddress: %s\n", inetAddress);
                        out.printf("\n");
                        count++;
                    }
                }
            }

            out.println("Choose server ip:");
            int id = -1;
            do {
                boolean valid = true;
                Scanner s = new Scanner(System.in);
                try {
                    id = Integer.parseInt(s.nextLine());
                } catch (NumberFormatException e) {
                  valid = false;
                }
                if (id < 0 || id > ipAddresses.size() || !valid) out.println("Choose a valid ip address.");
            } while (id < 0 || id > ipAddresses.size());
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

}
