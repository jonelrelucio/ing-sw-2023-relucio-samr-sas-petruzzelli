package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.HashMap;

/**
 * This class a type of common goal card that has a defined shape and occurrences
 * and minimum and maximum number of different tile's type value
 */
public class CommonGoalDifferentType implements CommonGoalCard{
    /**
     * Unique card's id
     */
    private final int id;
    /**
     * Number of rows
     */
    private final int row;
    /**
     * Number of columns
     */
    private final int col;
    /**
     * Occurrences of the shape to be found
     */
    private final int occurrence;
    /**
     * Minimum number of different tile's type
     */
    private final int minDifferentType;
    /**
     * Maximum number of different tile's type
     */
    private final int maxDifferentType;

    /**
     * Initialize the card
     * @param id
     * @param row
     * @param col
     * @param occurrence
     * @param min
     * @param max
     */
    public CommonGoalDifferentType(int id, int row, int col, int occurrence, int min, int max) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.occurrence = occurrence;
        this.minDifferentType = min;
        this.maxDifferentType = max;
    }

    /**
     * This method inspect the bookshelf matrix and fill the 'patternControl' hashmap
     * with tile's type as keys and their occurrences ad value. If the shape is found increment a counter by 1
     * and when the counter is equal to the occurrence value the pattern is found.
     * @param bookshelf
     * @return true if the pattern is found
     */
    public boolean checkPattern(ItemTile[][] bookshelf) {
        HashMap<ItemTileType, Integer> patternControl = new HashMap<>();
        int occurrenceControl = 0;

        for (int displayRow = 0; displayRow + row <= bookshelf.length; displayRow++) {
            for (int displayCol = 0; displayCol + col <= bookshelf[0].length; displayCol++) {
                if (bookshelf[displayRow][displayCol].getItemTileType() != ItemTileType.EMPTY) {
                    // iterate over the sub matrix representing the pattern scheme
                    for (int patternRow = displayRow; patternRow <= displayRow + row - 1; patternRow++) {
                        for (int patternCol = displayCol; patternCol <= displayCol + col - 1; patternCol++) {
                            if (bookshelf[patternRow][patternCol].getItemTileType() != ItemTileType.EMPTY) {
                                // if the HashMap has not a key of the current tile type then initialize a new key with value of 1
                                if (patternControl.isEmpty() || patternControl.get(bookshelf[patternRow][patternCol].getItemTileType()) == null) {
                                    patternControl.put(bookshelf[patternRow][patternCol].getItemTileType(), 1);
                                } else {
                                    patternControl.put(bookshelf[patternRow][patternCol].getItemTileType(), patternControl.get(bookshelf[patternRow][patternCol].getItemTileType()) + 1);
                                }
                            }
                        }
                    }

                    // count the number of elements into the patternControl hashmap, there must be exactly row * col elements.
                    int count = 0;
                    for (ItemTileType key : patternControl.keySet()) {
                        count = count + patternControl.get(key);
                    }
                    if (count == row * col && patternControl.size() >= minDifferentType && patternControl.size() <= maxDifferentType) occurrenceControl++;
                    if (occurrenceControl == occurrence) return true;
                    patternControl.clear();
                }
            }
        }

        return false;
    }

    /**
     * Getter for 'id' field
     * @return the card's id
     */
    public int getId() { return id; }
}
