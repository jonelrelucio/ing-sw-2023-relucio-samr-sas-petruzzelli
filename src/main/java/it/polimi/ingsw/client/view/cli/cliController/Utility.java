package it.polimi.ingsw.client.view.cli.cliController;

import java.util.Scanner;

public class Utility {

    static Scanner s = new Scanner(System.in);

    public static void printLoading()  {
        int index = 0;
        StringBuilder string = new StringBuilder("#");
        String missing = "                                  ";
        while (index <= 33) {
            System.out.printf("\rLoading [ "+string+missing+"]%d%%", (int)index*100/33);
            string.append("#");
            missing = missing.substring(0, 33-index);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            index++;
        }
        System.out.println();
    }


    public static int getNumInput(){
        try {
            return Integer.parseInt(s.nextLine());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid input. Enter a number.");
            return getNumInput();
        }
    }
}