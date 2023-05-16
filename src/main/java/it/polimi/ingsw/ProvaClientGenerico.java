package it.polimi.ingsw;

import it.polimi.ingsw.distributed.Serv;
import it.polimi.ingsw.distributed.newNetworking.Client;
import it.polimi.ingsw.distributed.newNetworking.RMIClient;
import it.polimi.ingsw.distributed.newNetworking.Server;
import it.polimi.ingsw.distributed.newNetworking.ServerStub;
import it.polimi.ingsw.distributed.newNetworking.SocketClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ProvaClientGenerico {
    public static void main(String[] args) throws RemoteException {
        Client client = null;
        String clientChoice;
        Server server = null;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("Scegli il tipo di connessione:\nr <- RMI\ns <- Socket");
            clientChoice = scanner.nextLine();

        }while(!(clientChoice.equals("r") || clientChoice.equals("s")));

        if(clientChoice.equals("r")){
            System.out.println("Scelto un client di tipo RMI");
            try{
                Registry reg = LocateRegistry.getRegistry();
                server = (Server) reg.lookup("server");
                client = new RMIClient(server);//passare il server a RMIClient
            } catch (RemoteException e) {
                System.err.println("Cannot locate RMI registry");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Scelto un client di tipo socket");
            server = new ServerStub("localhost", 1234);
            client = new SocketClient();
        }
        server.connect(client);

        while(true){
            String message = client.receiveMessageFromServer();
            System.out.println(message);
        }


    }
}
