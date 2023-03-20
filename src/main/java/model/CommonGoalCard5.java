public class CommonGoalCard5 implements CommonGoalCard{
    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        return bookshelf[0][0].getItemTileType() != ItemTileType.Empty &&
                bookshelf[0][0].getItemTileType() == bookshelf[0][bookshelf[0].length - 1].getItemTileType() &&
                bookshelf[0][0].getItemTileType() == bookshelf[bookshelf.length - 1][0].getItemTileType() &&
                bookshelf[0][0].getItemTileType() == bookshelf[bookshelf.length - 1][bookshelf[0].length - 1].getItemTileType();
    }
}
