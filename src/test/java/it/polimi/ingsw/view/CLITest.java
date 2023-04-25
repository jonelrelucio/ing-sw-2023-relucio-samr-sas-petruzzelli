package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CLITest {
    CLI view = new CLI(new GameModel(2));

    @Test
    public void testTitle() {
        CLI.startingScreen();
    }
}