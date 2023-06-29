package it.polimi.ingsw.distributed.integrated;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.guiController.ViewGui;
import it.polimi.ingsw.distributed.Server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * The Class that starts the client
 */
public class ClientMain {

    /**
     * The main method to start the client
     * @param args              the default arguments of main
     * @throws SocketException  throws socket exception if failed connection
     */
    public static void main(String[] args) throws SocketException {
        View view = null;
        String clientChoice;
        String viewChoice;
        Server server = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Scegli l'indirizzo Ip del server: ");
        String ip = scanner.nextLine();

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
        out.println("Choose Client ip:");
        Scanner s = new Scanner(System.in);
        int id = Integer.parseInt(s.nextLine());
        String ipAddress = ipAddresses.get(id);
        out.println("Client Ip Address: " + ipAddress);
        System.setProperty("java.rmi.server.hostname", ipAddress);


        do{
            System.out.println("Scegli il tipo di view:\nc <- CLI\ng <- GUI");
            viewChoice = scanner.nextLine();
        }while(!(viewChoice.equals("g") || viewChoice.equals("c")));

        if(viewChoice.equals("c")){
            view = new CLI();
        }else if(viewChoice.equals("g")){
            view = new ViewGui();

        }

        do{
            System.out.println("Scegli il tipo di connessione:\nr <- RMI\ns <- Socket");
            clientChoice = scanner.nextLine();

        }while(!(clientChoice.equals("r") || clientChoice.equals("s")));

        if(clientChoice.equals("r")){
            System.out.println("Scelto un client di tipo RMI");
            try{
                Registry reg = LocateRegistry.getRegistry(ip, 1099);
                server = (Server) reg.lookup("server");
                RMIClient client = new RMIClient(view, server);
                client.run();

            } catch (RemoteException e) {
                System.err.println("Cannot locate RMI registry");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Scelto un client di tipo socket");
            server = new ServerStub(ip, 1234);
            SocketClient client = new SocketClient((ServerStub) server, view);
            client.run();
        }
    }

    /**
     * Utility method to display the available ip address of the client
     * @param netint            NetWorkInterface
     */
    static void displayInterfaceInformation(NetworkInterface netint){
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
