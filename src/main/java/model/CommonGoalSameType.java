package model;

import java.util.HashMap;
import java.util.Objects;

public abstract class CommonGoalSameType implements CommonGoalCard{
    private final int row;
    private final int col;
    private final int occurrence;
    private final boolean adjacent;

    public CommonGoalSameType(int r, int c, int o, boolean a) {
        this.row = r;
        this.col = c;
        this.occurrence = o;
        this.adjacent = a;
    }

    public boolean checkPattern(ItemTile[][] bookshelf) {
        // patternControl register how many times the desired pattern with the same ItemTileType is found
        HashMap<ItemTileType, Integer> patternControl = new HashMap<>();

        // lockedTiles register the coordinates of the tiles already visited and registered into patternControl with their type
        String coordinates;
        HashMap<String, ItemTileType> lockedTiles = new HashMap<>();
        boolean coordinatesFound;

        for (int displayRow = 0; displayRow < bookshelf.length - row + 1; displayRow++) {
            for (int displayCol = 0; displayCol < bookshelf[0].length - col + 1; displayCol++) {

                // verify if the current tile is already been added to the lockedTiles HashMap
                // encode the coordinates as a String: "row,column"
                coordinates = displayRow + "," + displayCol;
                if (!adjacent) {
                    coordinatesFound = (lockedTiles.containsKey(coordinates) && lockedTiles.get(coordinates) == bookshelf[displayRow][displayCol].getItemTileType());
                } else {
                    coordinatesFound = lockedTiles.containsKey(coordinates);
                }

                // if the current tile is in the lockedTiles HashMap then skip to the next one
                if (!coordinatesFound) {
                    A:for (int patternRow = displayRow; patternRow < displayRow + row; patternRow++) {
                        for (int patternCol = displayCol; patternCol < displayCol + col; patternCol++) {
                            // verify if the tiles visited starting from the tile at {displayRow, displayCol} are the same type, if not then remove them from the lockedTiles HashMap and break
                            if (bookshelf[patternRow][patternCol].getItemTileType() != bookshelf[displayRow][displayCol].getItemTileType()) {
                                unlockTiles(bookshelf, lockedTiles, displayRow, displayCol, patternRow, patternCol);
                                break A;
                            }

                            // if the control on the type goes well then add the visited tile's coordinates and the type to the lockedTiles HashMap
                            lockedTiles.put(patternRow + "," + patternCol, bookshelf[patternRow][patternCol].getItemTileType());

                            if (!adjacent) {
                                // if the pattern schemas must be not adjacent then put the very next tiles to the pattern scheme into the lockedTiles ArrayList
                                if (patternRow == displayRow + row - 1 && patternRow + 1 < bookshelf.length) {
                                    lockedTiles.put((patternRow + 1) + "," + patternCol, bookshelf[patternRow][patternCol].getItemTileType());
                                }
                                if (patternCol == displayCol + col - 1 && patternCol + 1 < bookshelf[0].length) {
                                    lockedTiles.put(patternRow + "," + (patternCol + 1), bookshelf[patternRow][patternCol].getItemTileType());
                                }
                            }

                            // if the current visited tile is the same type of the other, and it's the last one to be checked, the pattern is found then update the patternControl HashMap
                            if (patternRow == displayRow + row - 1 && patternCol == displayCol + col - 1) {
                                if (patternControl.get(bookshelf[displayRow][displayCol].getItemTileType()) == null) {
                                    patternControl.put(bookshelf[displayRow][displayCol].getItemTileType(), 1);
                                } else {
                                    patternControl.put(bookshelf[displayRow][displayCol].getItemTileType(), patternControl.get(bookshelf[displayRow][displayCol].getItemTileType()) + 1);
                                }
                            }

                            // given the current tile's type (!= Empty) as key to the patternControl HashMap, if the related value is equal to the occurrence field then the commonGoalCard is obtained and return true
                            if (Objects.equals(patternControl.get(bookshelf[displayRow][displayCol].getItemTileType()), occurrence) && bookshelf[displayRow][displayCol].getItemTileType() != ItemTileType.EMPTY) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    // remove the tiles of the scheme starting from (displayRow, displayCol) that have been added to the lockedTiles HashMap until the last checked tile, from lockedTiles HashMap
    private void unlockTiles(ItemTile[][] bookshelf, HashMap<String, ItemTileType> lockedTiles, int displayRow, int displayCol, int patternRow, int patternCol) {
        B:for (int i = displayRow; i < displayRow + row; i++) {
            for (int j = displayCol; j < displayCol + col; j++) {
                if (i == patternRow && j == patternCol){
                    break B;
                } else if (!adjacent) {
                    if ((i == displayRow + row - 1 && i + 1 < bookshelf.length)) {
                        lockedTiles.remove((i + 1) + "," + j);
                    }
                    if ((j == displayCol + col - 1 && j + 1 < bookshelf[0].length)) {
                        lockedTiles.remove(i + "," + (j + 1));
                    }
                }
                lockedTiles.remove(i + "," + j);
            }
        }
    }
}
