//package it.polimi.ingsw;
//
//import it.polimi.ingsw.distributed.Client;
//import it.polimi.ingsw.distributed.ClientSkeleton;
//import it.polimi.ingsw.distributed.Server;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class ServerAppImpl extends UnicastRemoteObject implements ServerApp {
//
//    private static ServerAppImpl instance;
//    private final ExecutorService executorService = Executors.newCachedThreadPool();
//
//    private ArrayList<Client> clients = new ArrayList<>();
//
//
//
//
//    protected ServerAppImpl() throws RemoteException{
//
//    }
//
//    public static ServerAppImpl getInstance() throws RemoteException{
//        if(instance == null){
//            instance = new ServerAppImpl();
//        }
//        return instance;
//    }
//
//    public static void main(String[] args){
//        Thread rmiThread = new Thread(){
//
//            @Override
//            public void run() {
//                try{
//                    startRMI();
//                }catch(RemoteException e){
//                    System.err.println("Cannot start RMI server");
//                }
//            }
//        };
//        rmiThread.start();
//
//        Thread socketThread = new Thread(){
//            @Override
//            public void run() {
//                try{
//                    startSocket();
//                }catch(RemoteException e){
//                    System.err.println("Cannot start RMI server");
//                }
//            }
//        };
//
//        socketThread.start();
//
//        try{
//            rmiThread.join();
//            socketThread.join();
//        }catch(InterruptedException e){
//            System.err.println("No connection protocol available");
//        }
//    }
//
//    private static void startRMI() throws RemoteException{
//        ServerAppImpl server = getInstance();
//        Registry registry = LocateRegistry.getRegistry();
//        registry.rebind("server", server);
//    }
//
//    public static void startSocket() throws RemoteException{
//        ServerAppImpl instance = getInstance();
//        try(ServerSocket serverSocket = new ServerSocket(1234)){
//            while(true){
//                Socket socket = serverSocket.accept();
//                instance.executorService.submit(
//                        ()->{
//                            try{
//                                ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
//                            }catch(RemoteException e){
//                                System.out.println("Cannot receive from client, closing the connection");
//                            } finally{
//                                try{
//                                    socket.close();
//                                }catch (IOException e){
//                                    System.err.println("Cannot close socket");
//                                }
//                            }
//                        }
//
//                );
//            }
//        }catch (IOException e){
//            throw new RemoteException("Cannot start socket server", e);
//        }
//
//    }
//
//
//    @Override
//    public Server connect() throws RemoteException {
//        return null;
//    }
//}
