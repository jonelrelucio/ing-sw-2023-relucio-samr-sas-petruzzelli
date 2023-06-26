//package it.polimi.ingsw;
//
//import it.polimi.ingsw.distributed.Serv;
//
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//
//public class ProvaClientRMI {
//    public static void main(String[] args) {
//        try{
//            Registry reg = LocateRegistry.getRegistry();
//            Serv server = (Serv) reg.lookup("server");
//            server.sayHi();
//        } catch (RemoteException e) {
//            System.err.println("Cannot locate RMI registry");
//        } catch (NotBoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
