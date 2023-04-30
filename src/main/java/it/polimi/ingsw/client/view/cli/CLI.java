package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.cli.clicontroller.ControllerNewGame;
import it.polimi.ingsw.controller.events.GameEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.util.Observable;

public class CLI extends Observable<GameEvent> implements Runnable {

    ControllerNewGame controllerNewGame = new ControllerNewGame();
    GameModel model;

    public void run() {
        controllerNewGame.run();
    }


    public ControllerNewGame getControllerNewGame() {return controllerNewGame;}








    public static void main(String[] args) {

        /*final ItemTile[][] groupOfTwo =
                {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME)},
                          {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                        {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME)}};


        final ItemTile[][] bookshelf2 =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.TROPHY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.GAME),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.TROPHY),    new ItemTile(ItemTileType.GAME),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT)}};

        final ItemTile[][] bookshelf1 =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.CAT),  new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

        final ItemTile[][] eightPieces =
                {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                        {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                        {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                        {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)},
                        {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK)}};



        int numOfPlayers = 4;
        CLI.startingScreen();
        GameModel model = new GameModel(numOfPlayers);
        CLI view = new CLI(model);
        Player player1 = new Player("Jonel", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player1.getBookshelf().setBookshelfMatrix(groupOfTwo);
        Player player2 = new Player("Alessandro", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player2.getBookshelf().setBookshelfMatrix(bookshelf2);
        Player player3 = new Player("Dalila", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player3.getBookshelf().setBookshelfMatrix(eightPieces);
        Player player4 = new Player("Lucian", PersonalGoalCardBag.drawPersonalGoalCard(numOfPlayers), model.getBoard());
        player4.getBookshelf().setBookshelfMatrix(bookshelf1);

        model.addNewPlayer(player1);
        model.addNewPlayer(player2);
        model.addNewPlayer(player3);
        model.addNewPlayer(player4);

        player1.selectCoordinates(new int[]{2, 4});
        player1.selectCoordinates(new int[]{3, 4});
        player1.pickSelectedItemTiles();

        view.printAll();*/

    }


}
