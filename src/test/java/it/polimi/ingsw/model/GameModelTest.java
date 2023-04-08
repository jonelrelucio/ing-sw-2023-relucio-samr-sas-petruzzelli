package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard1;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard2;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.model.bag.PersonalGoalCardBag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameModelTest {
    private GameModel gameModel;
    private final ItemTile[][] bookshelf1 =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

    private final ItemTile[][] bookshelf2 =
            {       {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME)}};

    @Test
    public void testUpdatePlayerPoints1() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testUpdatePlayerPointsWinner() {
        int numOfPlayers = 2;
        gameModel = new GameModel(3);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
        gameModel.getCurrentPlayer().setWinner();
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testLoop() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
        gameModel.updateCurrentPlayerScore();
        boolean flag = true;
        int points = gameModel.getCurrentPlayer().getScore();
        for (int i = 0; i < 4; i++ ){
            gameModel.updateCurrentPlayerScore();
            if (points != gameModel.getCurrentPlayer().getScore()) flag = false;
            points = gameModel.getCurrentPlayer().getScore();
        }
        Assertions.assertTrue(flag);
    }

    @Test
    public void testLoopAndWinner() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
        gameModel.getCurrentPlayer().setWinner();
        gameModel.updateCurrentPlayerScore();
        boolean flag = true;
        int points = gameModel.getCurrentPlayer().getScore();
        for (int i = 0; i < 4; i++) {
            gameModel.updateCurrentPlayerScore();
            if (points != gameModel.getCurrentPlayer().getScore()) flag = false;
            points = gameModel.getCurrentPlayer().getScore();
        }

    }

    @Test
    public void testUpdatePlayerPoints2() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf2);
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testUpdatePlayerPointsWinner2() {
        int numOfPlayers = 2;
        gameModel = new GameModel(3);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf2);
        gameModel.getCurrentPlayer().setWinner();
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testLoop2() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf2);
        gameModel.updateCurrentPlayerScore();
        boolean flag = true;
        int points = gameModel.getCurrentPlayer().getScore();
        for (int i = 0; i < 4; i++ ){
            gameModel.updateCurrentPlayerScore();
            if (points != gameModel.getCurrentPlayer().getScore()) flag = false;
            points = gameModel.getCurrentPlayer().getScore();
        }
        Assertions.assertTrue(flag);
    }

    @Test
    public void testLoopAndWinner2() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers, new CommonGoalCard1(), new CommonGoalCard2()));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf2);
        gameModel.getCurrentPlayer().setWinner();
        gameModel.updateCurrentPlayerScore();
        boolean flag = true;
        int points = gameModel.getCurrentPlayer().getScore();
        for (int i = 0; i < 4; i++) {
            gameModel.updateCurrentPlayerScore();
            if (points != gameModel.getCurrentPlayer().getScore()) flag = false;
            points = gameModel.getCurrentPlayer().getScore();
        }

    }


}