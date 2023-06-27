package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.distributed.integrated.GameServer;
import it.polimi.ingsw.distributed.integrated.RMIRunnable;
import it.polimi.ingsw.distributed.integrated.SocketRunnable;

import java.net.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

import static java.lang.System.out;

public class GameServerMain {
    public static void main(String[] args) {

        try{
            GameServer server = new GameServer();
            ArrayList<String> ipAddresses = new ArrayList<>();

            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            int count = 0;
            for (NetworkInterface netint : Collections.list(nets)) {

                out.println("id: " + count);
                displayInterfaceInformation(netint);
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {

                    if(inetAddress instanceof Inet4Address)
                        ipAddresses.add(inetAddress.getHostAddress());

                }
                count++;
            }

            out.println("Choose server ip:");
            Scanner s = new Scanner(System.in);
            int id = Integer.parseInt(s.nextLine());
            String ipAddress = ipAddresses.get(id);
            out.println("chosen: " + ipAddress);

            System.setProperty("java.rmi.server.hostname", ipAddress);

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
        }catch(RemoteException | SocketException e){

        }



    }

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
