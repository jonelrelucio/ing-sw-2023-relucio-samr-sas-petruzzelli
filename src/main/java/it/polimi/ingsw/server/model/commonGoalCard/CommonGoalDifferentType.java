package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.HashMap;

public class CommonGoalDifferentType implements CommonGoalCard{
    private final int id;
    private final int row;
    private final int col;
    private final int occurrence;
    private final int minDifferentType;
    private final int maxDifferentType;

    public CommonGoalDifferentType(int id, int row, int col, int occurrence, int min, int max) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.occurrence = occurrence;
        this.minDifferentType = min;
        this.maxDifferentType = max;
    }

    public boolean checkPattern(ItemTile[][] bookshelf) {
        HashMap<ItemTileType, Integer> patternControl = new HashMap<>();
        int occurrenceControl = 0;

        for (int displayRow = 0; displayRow + row <= bookshelf.length; displayRow++) {
            for (int displayCol = 0; displayCol + col <= bookshelf[0].length; displayCol++) {
                if (bookshelf[displayRow][displayCol].getItemTileType() != ItemTileType.EMPTY) {
                    // iterate over the sub matrix representing the pattern scheme
                    for (int patternRow = displayRow; patternRow <= displayRow + row - 1; patternRow++) {
                        for (int patternCol = displayCol; patternCol <= displayCol + col - 1; patternCol++) {

                            // if the HashMap has not a key of the current tile type then initialize a new key with value of 1
                            if (patternControl.isEmpty() || patternControl.get(bookshelf[patternRow][patternCol].getItemTileType()) == null) {
                                patternControl.put(bookshelf[patternRow][patternCol].getItemTileType(), 1);
                            } else {
                                patternControl.put(bookshelf[patternRow][patternCol].getItemTileType(), patternControl.get(bookshelf[patternRow][patternCol].getItemTileType()) + 1);
                            }

                        }
                    }

                    if (patternControl.size() >= minDifferentType && patternControl.size() <= maxDifferentType) occurrenceControl++;
                    if (occurrenceControl == occurrence) return true;
                    patternControl.clear();
                }
            }
        }

        return false;
    }
}
