package it.polimi.ingsw.client;



import it.polimi.ingsw.AppClientRMI;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.LauchGui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class LauchClient {

    private static final Scanner scanner = new Scanner(System.in);

    private static String userInput(){
        return scanner.nextLine();
    }

    public static void main( String[] args ) {
        String answer;
        System.out.println("welcome to the game:\n");

        System.out.println("\u001B[32m" +"\n" +
                "  __  __                   _              _    __   _          \n" +
                " |  \\/  |                 | |            | |  / _| (_)         \n" +
                " | \\  / |  _   _     ___  | |__     ___  | | | |_   _    ___   \n" +
                " | |\\/| | | | | |   / __| | '_ \\   / _ \\ | | |  _| | |  / _ \\  \n" +
                " | |  | | | |_| |   \\__ \\ | | | | |  __/ | | | |   | | |  __/  \n" +
                " |_|  |_|  \\__, |   |___/ |_| |_|  \\___| |_| |_|   |_|  \\___|  \n" +
                "            __/ |                                              \n" +
                "           |___/                                               \n"+"\u001b[0m");

        System.out.println( "Select connection mode:\n" +
                "s <- SOCKET\n" +
                "r <- RMI" );
        answer = userInput().toLowerCase();
        while(!answer.equals("s")&& !answer.equals("r")){
            System.out.println("Invalid choice");
            System.out.println( "Select connection mode:\n" +
                    "s <- SOCKET\n" +
                    "r <- RMI" );
            answer = userInput().toLowerCase();
        }
        if (answer.equals("r")) {
            try {
                AppClientRMI.run();
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if (answer.equals("s")) {
            //istanzia s;
        }

        System.out.println("Select visualization mode:\n" + "c <- Command Line Interface\n" +
                "g <- Graphical User Interface" );
        answer = userInput().toLowerCase();
        while(!answer.equals("g" )&& !answer.equals("c")){
            System.out.println("Invalid choice");
            System.out.println("Select visualization mode:\n" + "c <- Command Line Interface\n" +
                    "g <- Graphical User Interface" );
            answer = userInput().toLowerCase();
        }
        if (answer.equals("c")) {
            //ViewCLI cli = new ViewCLI();
            //client = new Client(cli, "192.168.1.5", 5555);
            //client.start();
            CLI cli = new CLI();
            cli.run();
        }
        else if (answer.equals("g")) {
            LauchGui.main();
        }





    }
}
