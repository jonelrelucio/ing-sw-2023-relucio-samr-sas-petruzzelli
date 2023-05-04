package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.PersonalGoalCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalGoalCardTest {

        @Test
        public void testGetScore() {
            ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
            };
            PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);

            ItemTile[][] bookshelfMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.GAME)}
            };
            int score = personalGoalCard.getScore(bookshelfMatrix);
            assertEquals(2, score);
        }

        @Test
        public void testGetNumMatchingTiles() {
            ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
            };
            PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);

            ItemTile[][] bookshelfMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.GAME)}
            };
            int matchingTiles = personalGoalCard.getNumMatchingTiles(bookshelfMatrix);
            assertEquals(3, matchingTiles);
        }

        @Test
        public void testGetScoreInvalidArguments() {
            ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
            };
            PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);

            ItemTile[][] bookshelfMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.TROPHY)},
            };

            try { personalGoalCard.getScore(bookshelfMatrix); }
            catch (IllegalArgumentException ignored) {System.out.println("IllegalArgument exception called");}
            assertThrows(IllegalArgumentException.class, () -> personalGoalCard.getScore(bookshelfMatrix));
        }

        @Test
        public void testGetNumMatchingTilesInvalidArguments() {
            ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
            };
            PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);

            ItemTile[][] bookshelfMatrix = new ItemTile[][]{

                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.TROPHY)},
            };
            try { personalGoalCard.getNumMatchingTiles(bookshelfMatrix); }
            catch (IllegalArgumentException ignored) {System.out.println("IllegalArgument exception called");}

            assertThrows(IllegalArgumentException.class, () -> personalGoalCard.getNumMatchingTiles(bookshelfMatrix));
        }

    @Test
    public void testGetScoreInvalidArguments2() {
        ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
        };
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);

        ItemTile[][] bookshelfMatrix = new ItemTile[][]{
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), },
                {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT), },
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), }
        };

        try { personalGoalCard.getScore(bookshelfMatrix); }
        catch (IllegalArgumentException ignored) {System.out.println("IllegalArgument exception called");}
        assertThrows(IllegalArgumentException.class, () -> personalGoalCard.getScore(bookshelfMatrix));
    }

    @Test
    public void testGetNumMatchingTilesInvalidArguments2() {
        ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
        };
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);

        ItemTile[][] bookshelfMatrix = new ItemTile[][]{

                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), },
                {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT),},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), }
        };
        try { personalGoalCard.getNumMatchingTiles(bookshelfMatrix); }
        catch (IllegalArgumentException ignored) {System.out.println("IllegalArgument exception called");}

        assertThrows(IllegalArgumentException.class, () -> personalGoalCard.getNumMatchingTiles(bookshelfMatrix));
    }



    // Setter and getter tests
        @Test
        public void testSettersAndGetters() {
            ItemTile[][] personalGoalMatrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.GAME), new ItemTile(ItemTileType.CAT)}
            };
            PersonalGoalCard personalGoalCard = new PersonalGoalCard(personalGoalMatrix);
            ItemTile[][] matrix = new ItemTile[][]{
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY)}
            };
            personalGoalCard.setPersonalGoalCardMatrix(matrix);
            assertEquals(personalGoalCard.getPersonalGoalCardMatrix(), matrix);
        }

}
