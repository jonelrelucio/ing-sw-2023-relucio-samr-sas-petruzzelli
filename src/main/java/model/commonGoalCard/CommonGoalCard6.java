package model.commonGoalCard;

public class CommonGoalCard6 implements CommonGoalCard{
    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        boolean principalDiagonal;
        boolean secondaryDiagonal;
        int diagonalSize = 5;

        for (int row = 0; row + diagonalSize <= bookshelf.length; row++) {
            for (int col = 0; col + diagonalSize <= bookshelf[0].length; col++) {
                principalDiagonal = true;
                secondaryDiagonal = true;

                // search the diagonal with the same item tile type as a principal and secondary diagonal
                int y = 0;
                for (int i = row; i < row + diagonalSize; i++) {
                    int x = 0;
                    for (int j = col; j < col + diagonalSize; j++) {

                        // in the principal diagonal: set principalDiagonal to false just it finds a tile of a different type
                        if (bookshelf[row][col].getItemTileType() != ItemTileType.Empty && x == y && principalDiagonal) {
                            principalDiagonal = bookshelf[row][col].getItemTileType() == bookshelf[i][j].getItemTileType();

                            // if the last item tile in the principal diagonal is correct then the diagonal is complete
                            if (i == row + diagonalSize - 1 && j == col + diagonalSize - 1 && principalDiagonal) {
                                return true;
                            }
                        }

                        // in the secondary diagonal: set secondaryDiagonal to false just it finds a tile of a different type
                        if (bookshelf[row][col + diagonalSize - 1].getItemTileType() != ItemTileType.Empty && x + y == diagonalSize - 1 && secondaryDiagonal) {
                            secondaryDiagonal = bookshelf[row][col + diagonalSize - 1].getItemTileType() == bookshelf[i][j].getItemTileType();

                            // if the last item tile in the secondary diagonal is correct then the diagonal is complete
                            if (i == row + diagonalSize - 1 && j == col && secondaryDiagonal) {
                                return true;
                            }
                        }
                        x++;
                    }
                    y++;
                }
            }
        }

        return false;
    }
}
