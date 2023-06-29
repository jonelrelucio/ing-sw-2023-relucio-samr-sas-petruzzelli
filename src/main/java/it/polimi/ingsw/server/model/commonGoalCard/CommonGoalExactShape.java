package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

/**
 * This class represents a type of common goal card that has a defined shape
 */
public class CommonGoalExactShape implements CommonGoalCard {
    /**
     * Unique card's id
     */
    private final int id;
    /**
     * Coordinates of the cell that have to match their type
     */
    private final int[][] coords;
    /**
     * Flag that indicates to check if there is the shape flipped horizontally
     */
    private final boolean verticalSymmetric;

    /**
     * Initialize the card
     * @param id
     * @param coords
     * @param verticalSymmetric
     */
    public CommonGoalExactShape(int id, int[][] coords, boolean verticalSymmetric) {
        this.id = id;
        this.coords = coords;
        this.verticalSymmetric = verticalSymmetric;
    }

    /**
     * This method inspect the bookshelf matrix and check if the card's shape is present.
     * @param bookshelf
     * @return true if the shape is found
     * @see #flipHorizontal()
     */
    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        boolean found = true;

        A:for (int row = 0; row < bookshelf.length; row++) {
            for (int col = 0; col < bookshelf[0].length; col++) {
                if ((coords[row][col] == 1 && bookshelf[row][col].getItemTileType() == ItemTileType.EMPTY) || (coords[row][col] == 0 && bookshelf[row][col].getItemTileType() != ItemTileType.EMPTY)) {
                    found = false;
                    break A;
                }

            }
        }

        if (found) return true;

        if (verticalSymmetric) {
            found = true;
            int[][] coordsSym = flipHorizontal();
            B:for (int row = 0; row < bookshelf.length; row++) {
                for (int col = 0; col < bookshelf[0].length; col++) {
                    if ((coordsSym[row][col] == 1 && bookshelf[row][col].getItemTileType() == ItemTileType.EMPTY) || (coordsSym[row][col] == 0 && bookshelf[row][col].getItemTileType() != ItemTileType.EMPTY)) {
                        found = false;
                        break B;
                    }

                }
            }
        }

        return found;
    }

    /**
     * This method flip horizontally the coordinates of coords field
     * @return the new coordinates
     */
    private int[][] flipHorizontal() {
        int[][] newCoords = new int[coords.length][coords[0].length];

        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                newCoords[row][col] = coords[row][coords[0].length - 1 - col];
            }
        }

        return newCoords;
    }

    /**
     * Getter for 'id' field
     * @return the card's id
     */
    public int getId() { return id; }
}
