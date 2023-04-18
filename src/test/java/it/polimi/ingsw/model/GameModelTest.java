package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.model.util.CircularArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

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
        gameModel = new GameModel(numOfPlayers);gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testUpdatePlayerPointsWinner() {
        int numOfPlayers = 2;
        gameModel = new GameModel(3);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf1);
        gameModel.getCurrentPlayer().setWinner();
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testLoop() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
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
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
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
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf2);
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testUpdatePlayerPointsWinner2() {
        int numOfPlayers = 2;
        gameModel = new GameModel(3);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
        gameModel.getCurrentPlayer().getBookshelf().setBookshelfMatrix(bookshelf2);
        gameModel.getCurrentPlayer().setWinner();
        gameModel.updateCurrentPlayerScore();
        System.out.println(gameModel.getCurrentPlayer().getScore());
    }

    @Test
    public void testLoop2() {
        int numOfPlayers = 2;
        gameModel = new GameModel(numOfPlayers);
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
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
        gameModel.setCommonGoalCardDeck(new CommonGoalCardDeck(numOfPlayers));
        gameModel.setCurrentPlayer(new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers, 2), new Board(numOfPlayers)));
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


    @Test
    void testGetNumOfPlayer() {
        gameModel = new GameModel(2);
        assertEquals(2, gameModel.getNumOfPlayer());
    }

    @Test
    void testGetPlayerList() {
        gameModel = new GameModel(2);
        Board board = new Board(2);
        PersonalGoalCard personalGoalCard = PersonalGoalCardBag.drawPersonalGoalCard(2);
        gameModel.getPlayerList().add(new Player("Lucian", personalGoalCard, board));
        gameModel.getPlayerList().add(new Player("Jonel", personalGoalCard, board));
        gameModel.setCurrentPlayer(gameModel.getPlayerList().get(0));
        CircularArrayList<Player> playerList = gameModel.getPlayerList();
        assertEquals(2, playerList.size());
        assertEquals("Lucian", playerList.get(0).getNickname());
        assertEquals("Jonel", playerList.get(1).getNickname());
    }

    @Test
    public void testGetWinner() {
        GameModel gameModel = new GameModel(3);
        Board board = new Board(3);
        PersonalGoalCard personalGoalCard = PersonalGoalCardBag.drawPersonalGoalCard(3);
        gameModel.getPlayerList().add(new Player("Lucian", personalGoalCard, board));
        gameModel.initCurrentPlayer();
        assertThrows(IllegalCallerException.class, gameModel::getWinner);
    }

    @Test
    public void testUpdateNextPlayer() {
        GameModel gameModel = new GameModel(2);
        Board board = new Board(2);
        PersonalGoalCard personalGoalCard = PersonalGoalCardBag.drawPersonalGoalCard(2);
        gameModel.getPlayerList().add(new Player("Lucian", personalGoalCard, board));
        gameModel.getPlayerList().add(new Player("Jonel", personalGoalCard, board));
        gameModel.initCurrentPlayer();
        Player currentPlayer = gameModel.getCurrentPlayer();
        assertEquals(currentPlayer, gameModel.getPlayerList().get(0));
        gameModel.updateNextPlayer();
        Player nextPlayer = gameModel.getCurrentPlayer();
        assertEquals(nextPlayer, gameModel.getPlayerList().get(1));
    }

    @Test
    public void testUpdateNumOfRounds() {
        GameModel gameModel = new GameModel(3);
        int numOfRounds = gameModel.getNumOfRounds();
        gameModel.updateNumOfRounds();
        assertEquals(numOfRounds + 1, gameModel.getNumOfRounds());
    }

    @Test
    public void testGettersAndSetters() {
        gameModel = new GameModel(4);
        gameModel.setNumOfPlayer(4);
        Board board = new Board(4);
        PersonalGoalCard personalGoalCard = PersonalGoalCardBag.drawPersonalGoalCard(4);
        assertEquals(4, gameModel.getNumOfPlayer());

        CircularArrayList<Player> playerList = new CircularArrayList<>();
        playerList.add(new Player("Dalila", personalGoalCard, board));
        playerList.add(new Player("Lucian", personalGoalCard, board));
        gameModel.setPlayerList(playerList);
        assertEquals(playerList, gameModel.getPlayerList());

        CommonGoalCardDeck commonGoalCardDeck = new CommonGoalCardDeck(4);
        gameModel.setCommonGoalCardDeck(commonGoalCardDeck);
        assertEquals(commonGoalCardDeck, gameModel.getCommonGoalCardDeck());

        gameModel.setBoard(board);
        assertEquals(board, gameModel.getBoard());

        State state = State.END;
        gameModel.setState(state);
        assertEquals(state, gameModel.getState());

        int numOfRounds = 2;
        gameModel.setNumOfRounds(numOfRounds);
        assertEquals(numOfRounds, gameModel.getNumOfRounds());

        Player currentPlayer = new Player("Alessandro", personalGoalCard, board);
        gameModel.setCurrentPlayer(currentPlayer);
        assertEquals(currentPlayer, gameModel.getCurrentPlayer());
    }


}