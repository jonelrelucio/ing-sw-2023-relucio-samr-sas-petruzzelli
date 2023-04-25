package it.polimi.ingsw.view;

import it.polimi.ingsw.events.GameEvent;
import it.polimi.ingsw.events.PlayerEvent;
import it.polimi.ingsw.util.Observable;

import java.util.Scanner;

public class PreMatchCliUI extends Observable<GameEvent> implements Runnable {


    @Override
    public void run() {
        String name = askName();
        setChanged();//poi vedere se è da togliere, modificando l'observable
        notifyObservers(new PlayerEvent(name, 0));

    }

    private String askName(){
        System.out.print("Please, choose your userName:");
        Scanner scanner = new Scanner(System.in);
        String chosenName = scanner.next();
        return chosenName;
    }

    private int askNumOfPlayers(){
        System.out.println("Please, choose the number of Players");
        Scanner scanner = new Scanner(System.in);
        int chosenNumOfPlayers = scanner.nextInt();
        return chosenNumOfPlayers;
    }

}
