package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard1;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard2;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.model.factory.PersonalGoalCardBag;
import org.junit.jupiter.api.Test;

public class GameControllerTest {
        private GameModel gameModel;
        private final ItemTile[][] bookshelf1 =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

        private final ItemTile[][] bookshelf7 =
                {       {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)},
                        {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)},
                        {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME)},
                        {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)},
                        {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.GAME)},
                        {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME)}};

        @Test
        public void checkUpdatePlayerPoints1() {
                int numOfPlayers = 2;
                gameModel = new GameModel(numOfPlayers);
                gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
                gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
                gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
                GameController gc = new GameController(gameModel);
                gc.updateCurrentPlayerScore();
                System.out.println(gameModel.getCurrentPlayer().getScore());
        }

        @Test
        public void checkUpdatePlayerPointsWinner() {
                int numOfPlayers = 2;
                gameModel = new GameModel(3);
                CommonGoalCardDeck deck = new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2());
                gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
                gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
                gameModel.getCurrentPlayer().setWinner();
                GameController gc = new GameController(gameModel);
                gc.updateCurrentPlayerScore();
                System.out.println(gameModel.getCurrentPlayer().getScore());
        }


}