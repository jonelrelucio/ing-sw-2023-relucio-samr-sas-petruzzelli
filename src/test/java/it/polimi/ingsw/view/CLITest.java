package it.polimi.ingsw.view;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.model.GameModel;
import org.junit.jupiter.api.Test;

public class CLITest {
    CLI view = new CLI(new GameModel(2));

    @Test
    public void testTitle() {
        CLI.startingScreen();
    }
}