package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

public class CommonGoalExactShape implements CommonGoalCard {
    private final int id;
    private final int[][] coords;
    private final boolean verticalSymmetric;

    public CommonGoalExactShape(int id, int[][] coords, boolean verticalSymmetric) {
        this.id = id;
        this.coords = coords;
        this.verticalSymmetric = verticalSymmetric;
    }

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
                    if ((coords[row][col] == 1 && bookshelf[row][col].getItemTileType() == ItemTileType.EMPTY) || (coords[row][col] == 0 && bookshelf[row][col].getItemTileType() != ItemTileType.EMPTY)) {
                        found = false;
                        break B;
                    }

                }
            }
        }

        return found;
    }

    private int[][] flipHorizontal() {
        int[][] newCoords = new int[coords.length][coords[0].length];

        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                newCoords[row][col] = coords[row][coords[0].length - 1 - col];
            }
        }

        return newCoords;
    }
}
