package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.bag.CommonGoalCardBag;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    private GameModel gameModel;


    @Test
    public void checkUpdatePlayerPoints() {
        int numOfPlayers = 3;
        CommonGoalCardDeck deck = CommonGoalCardBag.commonGoalCardDeckBuilder(numOfPlayers);
        gameModel.setCurrentPlayer(new Player("Alessandro"));

    }
}