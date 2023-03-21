package model.commonGoalCard;

import model.ItemTile;
import model.ItemTileType;

public class CommonGoalCard8 implements CommonGoalCard{
    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        boolean principalDiagonal, secondaryDiagonal;
        boolean principalFound = false;
        boolean secondaryFound = false;
        int diagonalSize = 3;

        for (int row = 0; row + diagonalSize <= bookshelf.length; row++) {
            for (int col = 0; col + diagonalSize <= bookshelf[0].length; col++) {
                principalDiagonal = true;
                secondaryDiagonal = true;

                // search the diagonal with the same item tile type as a principal and secondary diagonal
                int x = 0;
                for (int i = row; i < row + diagonalSize; i++) {
                    int y = 0;
                    for (int j = col; j < col + diagonalSize; j++) {

                        // in the principal diagonal: set principalDiagonal to false just it finds a tile of a different type
                        if (bookshelf[row][col].getItemTileType() != ItemTileType.EMPTY && x == y && principalDiagonal) {
                            principalDiagonal = bookshelf[row][col].getItemTileType() == bookshelf[i][j].getItemTileType();

                            // if the last item tile in the principal diagonal is correct then the diagonal is complete
                            if (i == row + diagonalSize - 1 && j == col + diagonalSize - 1 && principalDiagonal) {
                                principalFound = true;
                            }
                        }

                        // in the secondary diagonal: set secondaryDiagonal to false just it finds a tile of a different type
                        if (bookshelf[row][col + diagonalSize - 1].getItemTileType() != ItemTileType.EMPTY && x + y == diagonalSize - 1 && secondaryDiagonal) {
                            secondaryDiagonal = bookshelf[row][col + diagonalSize - 1].getItemTileType() == bookshelf[i][j].getItemTileType();

                            // if the last item tile in the secondary diagonal is correct then the diagonal is complete
                            if (i == row + diagonalSize - 1 && j == col && secondaryDiagonal) {
                                secondaryFound = true;
                            }
                        }

                        // if both the diagonals are found then return true;
                        if (principalFound && secondaryFound) return true;
                        y++;
                    }
                    x++;
                }
            }
        }

        return false;
    }
}
