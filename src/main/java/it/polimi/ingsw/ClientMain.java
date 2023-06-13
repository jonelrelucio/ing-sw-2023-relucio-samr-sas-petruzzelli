/*
package it.polimi.ingsw;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.newNetworking.RMIClientNew;
import it.polimi.ingsw.distributed.newNetworking.ServerStubNew;
import it.polimi.ingsw.distributed.newNetworking.SocketClientNew;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws RemoteException {
        View view = null;
        Client client = null;
        String clientChoice;
        String viewChoice;
        Server server = null;
        Scanner scanner = new Scanner(System.in);

        do{
            System.out.println("Scegli il tipo di view:\nc <- CLI\ng <- GUI");
            viewChoice = scanner.nextLine();
        }while(!(viewChoice.equals("g") || viewChoice.equals("c")));

        if(viewChoice.equals("c")){
            view = new CLI();
        }else if(viewChoice.equals("g")){
            view = new GUI();
        }


        do{
            System.out.println("Scegli il tipo di connessione:\nr <- RMI\ns <- Socket");
            clientChoice = scanner.nextLine();

        }while(!(clientChoice.equals("r") || clientChoice.equals("s")));

        if(clientChoice.equals("r")){
            System.out.println("Scelto un client di tipo RMI");
            try{
                Registry reg = LocateRegistry.getRegistry();
                server = (Server) reg.lookup("server");
                //client = new RMIClient(server);//passare il server a RMIClient
                client = new RMIClientNew(view, server);

            } catch (RemoteException e) {
                System.err.println("Cannot locate RMI registry");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Scelto un client di tipo socket");
            server = new ServerStubNew("localhost", 1234);
            client = new SocketClientNew();
        }
        //server.connect(client);
        view.run();

    }
}
*/
