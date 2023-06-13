/*
package it.polimi.ingsw.distributed;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class BigServer extends UnicastRemoteObject {

    private static final long serialVersionUID = 1L;
    private List<ClientHandler> clients = new ArrayList<>();
    private int port;

    public BigServer(int port) throws RemoteException {
        this.port = port;
    }

    public void start() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            // Start the RMI server
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServerInterface", this);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        }
    }
}
*/
