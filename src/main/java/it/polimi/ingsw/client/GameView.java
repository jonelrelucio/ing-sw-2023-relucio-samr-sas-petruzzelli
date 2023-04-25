package it.polimi.ingsw.client;

import java.util.Scanner;

public class GameView {
    Scanner s = new Scanner(System.in);

    public int[] askPlayerSelectedTiles() {
        System.out.println("Select tile from board: ");
        return new int[]{Integer.parseInt(s.nextLine())};
    }
}
