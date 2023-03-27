package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonGoalCard3 implements CommonGoalCard{

    private final int height = 2;
    private final int width = 2;
    private final int occurrence = 2;

    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        // "locked" is used to store the coordinates of the tiles that cannot be checked (i.e. the tiles that are already part of a correct scheme, or their adjacent tiles)
        ArrayList<int[]> lockedTiles = new ArrayList<>();
        int counter = occurrence;

        // read the bookshelf matrix from the bottom left corner to the top right corner
        for (int displayRow = bookshelf.length - 1; displayRow - height + 1 >= 0; displayRow--) {
            for (int displayCol = 0; displayCol + width - 1 < bookshelf[0].length; displayCol++) {

                // check if the current tile is not locked, then proceed with the algorithm:
                // if all the tiles we are checking are the same type then decrement the counter till it is > 0
                // when the counter reaches the value of zero then return true: the goal is obtained by the player
                if (bookshelf[displayRow][displayCol].getItemTileType() != ItemTileType.EMPTY && !isInList(lockedTiles, new int[]{displayRow, displayCol})) {
                    if (checkEquals(displayRow, displayCol, lockedTiles, bookshelf)) counter--;
                    if (counter == 0) return true;
                }
            }
        }

        return false;
    }

    public boolean checkEquals(int row, int col, ArrayList<int[]> locked, ItemTile[][] bookshelf) {

        // read the sub matrix and if the current tile type is equal to the type of the first one put the coordinates of the current tile into the lockedTiles ArrayList
        // when the current tile type is different return false: the pattern scheme is not found
        ItemTileType type = bookshelf[row][col].getItemTileType();

        for (int i = row; i >= row - height + 1; i--) {
            for (int j = col; j <= col + width - 1; j++) {
                if (bookshelf[i][j].getItemTileType() == type && !isInList(locked, new int[]{i, j})) {

                    // if the tiles adjacent to the scheme we are checking are the same type then return false
                    if (i == row && i + 1 < bookshelf.length && bookshelf[i + 1][j].getItemTileType() == type) {
                        unlockTiles(row, col, i, j, locked, bookshelf.length, bookshelf[0].length);
                        return false;
                    }
                    if (i == row - height + 1 && i - 1 >= 0 && bookshelf[i - 1][j].getItemTileType() == type) {
                        unlockTiles(row, col, i, j, locked, bookshelf.length, bookshelf[0].length);
                        return false;
                    }
                    if (j == col && j - 1 >= 0 && bookshelf[i][j - 1].getItemTileType() == type) {
                        unlockTiles(row, col, i, j, locked, bookshelf.length, bookshelf[0].length);
                        return false;
                    }
                    if (j == col + width - 1 && j + 1 < bookshelf[0].length && bookshelf[i][j + 1].getItemTileType() == type) {
                        unlockTiles(row, col, i, j, locked, bookshelf.length, bookshelf[0].length);
                        return false;
                    }

                    // add the current tile and its adjacent tiles to lockedTiles
                    locked.add(new int[]{i, j});

                    if (i == row && i < bookshelf.length - 1) locked.add(new int[]{i + 1, j});
                    if (i == row - height + 1 && i > 0) locked.add(new int[]{i - 1, j});
                    if (j == col && j > 0) locked.add(new int[]{i, j - 1});
                    if (j == col + width - 1 && j < bookshelf[0].length - 1) locked.add(new int[]{i, j + 1});

                } else {
                    unlockTiles(row, col, i, j, locked, bookshelf.length, bookshelf[0].length);
                    return false;
                }
            }
        }

        return true;
    }

    public void unlockTiles(int row, int col, int tillRow, int tillCol, ArrayList<int[]> locked, int bookshelfHeight, int bookshelfWidth) {
        for (int i = row; i >= row - height + 1; i--) {
            for (int j = col; j <= col + width - 1; j++) {

                if (i != tillRow && j != tillCol) break;

                int[] coords = {i, j};
                locked.removeIf(item -> Arrays.equals(item, coords));

                // unlock the adjacent tiles
                if (i == row && i < bookshelfHeight - 1) {
                    int[] coordsSouth = {i + 1, j};
                    locked.removeIf(item -> Arrays.equals(item, coordsSouth));
                }
                if (i == row - height + 1 && i > 0) {
                    int[] coordsNorth = {i - 1, j};
                    locked.removeIf(item -> Arrays.equals(item, coordsNorth));
                }
                if (j == col && j > 0) {
                    int[] coordsWest = {i, j - 1};
                    locked.removeIf(item -> Arrays.equals(item, coordsWest));
                }
                if (j == col + width - 1 && j < bookshelfWidth - 1) {
                    int[] coordsEast = {i, j + 1};
                    locked.removeIf(item -> Arrays.equals(item, coordsEast));
                }
            }
        }
    }

    public boolean isInList(List<int[]> list, int[] target) {
        for (int[] item : list) {
            if (Arrays.equals(item, target)) return true;
        }

        return false;
    }
}
