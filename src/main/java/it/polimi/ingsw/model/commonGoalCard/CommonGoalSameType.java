package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

import java.util.HashMap;

public abstract class CommonGoalSameType implements CommonGoalCard {
    private final int num;
    private final int occurrence;

    public CommonGoalSameType(int n, int o) {
        this.num = n;
        this.occurrence = o;
    }

    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {

        HashMap<String, ItemTileType> locked = new HashMap<>();
        int counter = occurrence;

        for (int bookshelfRow = bookshelf.length - 1; bookshelfRow >= 0; bookshelfRow--) {
            for (int bookshelfCol = 0; bookshelfCol < bookshelf[0].length; bookshelfCol++) {
                if (!locked.containsKey(bookshelfRow + "," + bookshelfCol) && bookshelf[bookshelfRow][bookshelfCol].getItemTileType() != ItemTileType.EMPTY) {
                    if (checkAdjacent(bookshelf, bookshelf[bookshelfRow][bookshelfCol].getItemTileType(), locked, bookshelfRow, bookshelfCol, 0) == num) {
                        counter--;
                    }
                }
                if (counter == 0) return true;
            }
        }

        return false;
    }

    public int checkAdjacent(ItemTile[][] bookshelf, ItemTileType type, HashMap<String, ItemTileType> locked, int row, int col, int count) {
        if (!locked.containsKey(row + "," + col) && bookshelf[row][col].getItemTileType() == type) {
            locked.put(row + "," + col, type);
            count++;
            if (count == num) {
                lockAdjacent(bookshelf, locked, row, col);
                return num;
            }

            // check above
            if (row - 1 >= 0) {
                count = checkAdjacent(bookshelf, type, locked, row - 1, col, count);
                if (count == num) {
                    lockAdjacent(bookshelf, locked, row - 1, col);
                    return num;
                }
            }

            // check to the right
            if (col + 1 < bookshelf[0].length) {
                count = checkAdjacent(bookshelf, type, locked, row, col + 1, count);
                if (count == num) {
                    lockAdjacent(bookshelf, locked, row, col + 1);
                    return num;
                }
            }

            // check below
            if (row + 1 < bookshelf.length) {
                count = checkAdjacent(bookshelf, type, locked, row + 1, col, count);
                if (count == num) {
                    lockAdjacent(bookshelf, locked, row + 1, col);
                    return num;
                }
            }

            // check to the left
            if (col - 1 >= 0) {
                count = checkAdjacent(bookshelf, type, locked, row, col - 1, count);
                if (count == num) {
                    lockAdjacent(bookshelf, locked, row, col - 1);
                    return num;
                }
            }
        }

        return count;
    }

    public void lockAdjacent (ItemTile[][] bookshelf, HashMap<String, ItemTileType> locked, int row, int col) {
        if (row - 1 >= 0) locked.put((row - 1) + "," + col, bookshelf[row - 1][col].getItemTileType());
        if (col + 1 < bookshelf[0].length) locked.put(row + "," + (col + 1), bookshelf[row][col + 1].getItemTileType());
        if (row + 1 < bookshelf.length) locked.put((row + 1) + "," + col, bookshelf[row + 1][col].getItemTileType());
        if (col - 1 >= 0) locked.put(row + "," + (col - 1), bookshelf[row][col - 1].getItemTileType());
    }
}
