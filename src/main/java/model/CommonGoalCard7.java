public class CommonGoalCard7 implements CommonGoalCard {
    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        int height = 5;
        boolean increasing, decreasing;

        for (int col = 0; col + height <= bookshelf[0].length; col++) {
            increasing = true;
            decreasing = true;

            int y = height - 1;
            for (int i = bookshelf.length - 1; i >= bookshelf.length - height; i--) {
                int x = 0;
                for (int j = col; j < col + height; j++) {

                    // if the item tiles in the secondary diagonal are not empty then control if the tiles just above are empty, if so the scheme (increasing) is found
                    if (bookshelf[i][j].getItemTileType() != ItemTileType.Empty && x + y == height - 1 && increasing) {
                        increasing = i != 0 && bookshelf[i - 1][j].getItemTileType() == ItemTileType.Empty;

                        // if the last item tile in the secondary diagonal is correct then the diagonal is complete
                        if (i == bookshelf.length - height && j == col + height - 1 && increasing) {
                            return true;
                        }
                    }

                    // if the item tiles in the principal diagonal are not empty then control if the tiles just above are empty, if so the scheme (decreasing) is found
                    if (bookshelf[i][j].getItemTileType() != ItemTileType.Empty && x == y && decreasing) {
                        decreasing = i != 0 && bookshelf[i - 1][j].getItemTileType() == ItemTileType.Empty;

                        // if the last item tile in the secondary diagonal is correct then the diagonal is complete
                        if (i == bookshelf.length - height && j == col && decreasing) {
                            return true;
                        }
                    }
                    x++;
                }
                y--;
            }

        }

        return false;
    }
}
