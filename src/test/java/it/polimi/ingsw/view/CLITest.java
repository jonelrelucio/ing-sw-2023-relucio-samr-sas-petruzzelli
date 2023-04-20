package it.polimi.ingsw.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CLITest {
    CLI view = new CLI();

    @Test
    public void testTitle() {
        view.startingScreen();
    }
}