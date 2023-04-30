package it.polimi.ingsw.client.view.cli.clicontroller;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.Const;

public class ControllerStart {

    ControllerNewGame constrollerNewGame;

    public void startingScreen() {
        System.out.println(Const.title);
        int input;
        do {
            input = Utility.getNumInput();
            if (input <= 0 || input >= 4 ) System.out.println(Const.RED_BOLD_BRIGHT +"Invalid input. Enter a number from 1 to 3."+Const.RESET);
        }   while(input <= 0 || input >= 4  );
        switch (input) {
            case 1 -> constrollerNewGame.run();
            case 2 -> System.exit(1);
            case 3 -> System.exit(0);
        }
    }


}
