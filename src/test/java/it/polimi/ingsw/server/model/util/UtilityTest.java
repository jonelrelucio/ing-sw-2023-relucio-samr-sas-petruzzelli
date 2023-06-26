package it.polimi.ingsw.server.model.util;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.PersonalGoalCard;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    public void testSerializeArrayOfItemId() {
        CircularArrayList<Player> playerList = new CircularArrayList<>();
        Board board = new Board(2);

        ItemTile[][] matrix = new ItemTile[][]{
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)},
                {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.TROPHY)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)}
        };
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(matrix);
        Player player = new Player("Jonel", personalGoalCard, board);
        player.getBookshelf().setBookshelfMatrix(matrix);
        playerList.add(player);


        ItemTile[][] matrix2 = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.EMPTY)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)}
        };
        PersonalGoalCard personalGoalCard2 = new PersonalGoalCard(matrix2);
        Player player2 = new Player("Lucian", personalGoalCard2, board);
        player2.getBookshelf().setBookshelfMatrix(matrix2);
        playerList.add(player2);

        int[][][] serialized = Utility.serializeArrayOfItemId(playerList);

        for (int i = 0; i < playerList.size(); i++ ) {
            System.out.println(playerList.get(i).getNickname());
            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    System.out.print(serialized[i][j][k] + " ");
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }


    }

}