package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class CommonGoalShape implements CommonGoalCard{
    private final int id; // unique id
    private final int width; // width of the matrix containing the shape
    private final int height; // height of the matrix containing the shape
    private final int[][] coords; // coordinates of the cell that have to match their type
    private final int occurrence; // occurrences of the shape to be found
    private final boolean verticalSymmetric;
    private final boolean horizontalSymmetric;
    private final boolean diagonalSymmetric;
    private final boolean groupsOfSameType;
    private final boolean separated;

    public CommonGoalShape(int id, int width, int height, int[][] coords, int occurrence, boolean verticalSymmetric, boolean horizontalSymmetric, boolean diagonalSymmetric, boolean groupsOfSameType, boolean separated) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.coords = coords;
        this.occurrence = occurrence;
        this.verticalSymmetric = verticalSymmetric;
        this.horizontalSymmetric = horizontalSymmetric;
        this.diagonalSymmetric = diagonalSymmetric;
        this.groupsOfSameType = groupsOfSameType;
        this.separated = separated;
    }

    public boolean checkPattern(ItemTile[][] bookshelf) {
        HashMap<ItemTileType, ArrayList<int[]>> locked = new HashMap<>();
        HashMap<ItemTileType, ArrayList<int[]>> lockedAdjacent = new HashMap<>();
        HashMap<ItemTileType, Integer> map;
        HashMap<ItemTileType, Integer> mapTemp;

        map = findShape(bookshelf, coords, locked, lockedAdjacent);

        if (verticalSymmetric) {
            mapTemp = findShape(bookshelf, flipHorizontal(), locked, lockedAdjacent);
            for (ItemTileType c : mapTemp.keySet()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + mapTemp.get(c));
                } else {
                    map.put(c, mapTemp.get(c));
                }
            }
        }

        if (horizontalSymmetric) {
            mapTemp = findShape(bookshelf, flipVertical(), locked, lockedAdjacent);
            for (ItemTileType c : mapTemp.keySet()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + mapTemp.get(c));
                } else {
                    map.put(c, mapTemp.get(c));
                }
            }
        }

        if (diagonalSymmetric) {
            mapTemp = findShape(bookshelf, flipDiagonal(), locked, lockedAdjacent);
            for (ItemTileType c : mapTemp.keySet()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + mapTemp.get(c));
                } else {
                    map.put(c, mapTemp.get(c));
                }
            }
        }

        if (groupsOfSameType) {
            for (ItemTileType c : map.keySet()) {
                if (map.get(c) >= occurrence) return true;
            }
        } else {
            int count = 0;
            for (ItemTileType c : map.keySet()) {
                count = count + map.get(c);
            }
            if (count >= occurrence) return true;
        }

        return false;
    }

    public HashMap<ItemTileType, Integer> findShape(ItemTile[][] bookshelf, int[][] coords, HashMap<ItemTileType, ArrayList<int[]>> locked, HashMap<ItemTileType, ArrayList<int[]>> lockedAdjacent) {
        ItemTileType matchingType;
        boolean found;
        boolean skip;
        HashMap<ItemTileType, Integer> map = new HashMap<>();

        for (int row = 0; row + height - 1 <= bookshelf.length - 1; row++) {
            for (int col = 0; col + width - 1 <= bookshelf[0].length - 1; col++) {

                matchingType = bookshelf[row + coords[0][0]][col + coords[0][1]].getItemTileType();
                if (matchingType == ItemTileType.EMPTY) continue;
                found = true;
                skip = false;

                A:for (int[] c : coords) {
                    if (separated && groupsOfSameType) {
                        if (lockedAdjacent.get(matchingType) != null) {
                            for (int[] cordLocked : lockedAdjacent.get(matchingType)) {
                                if (Arrays.equals(cordLocked, new int[]{row + c[0], col + c[1]})) {
                                    skip = true;
                                    break A;
                                }
                            }
                        }
                    } else if (separated) {
                        for (ItemTileType key : locked.keySet()) {
                            for (int[] cordLocked : locked.get(key)) {
                                if (Arrays.equals(cordLocked, new int[]{row + c[0], col + c[1]})) {
                                    skip = true;
                                    break A;
                                }
                            }
                        }
                        for (ItemTileType key : lockedAdjacent.keySet()) {
                            for (int[] cordLocked : lockedAdjacent.get(key)) {
                                if (Arrays.equals(cordLocked, new int[]{row + c[0], col + c[1]})) {
                                    skip = true;
                                    break A;
                                }
                            }
                        }
                    } else {
                        if (locked.containsKey(matchingType)) {
                            for (int[] cordLocked : locked.get(matchingType)) {
                                if (Arrays.equals(cordLocked, new int[]{row + c[0], col + c[1]})) {
                                    skip = true;
                                    break A;
                                }
                            }
                        }
                    }

                    if (bookshelf[row + c[0]][col + c[1]].getItemTileType() != matchingType) {
                        found = false;
                        break;
                    }
                    if (!locked.containsKey(matchingType)) {
                        ArrayList<int[]> currentCoords = new ArrayList<>();
                        currentCoords.add(new int[] {row + c[0], col + c[1]});
                        locked.put(matchingType, currentCoords);
                    } else locked.get(matchingType).add(new int[] {row + c[0], col + c[1]});
                }

                if (skip) continue;

                if (found) {
                    // if separated is true then lock adjacent cells
                    if (separated) {
                        for (int[] c : coords) {
                            boolean lockAbove = false;
                            boolean lockRight = false;
                            boolean lockBelow = false;
                            boolean lockLeft = false;

                            int[] above = {row + (c[0] - 1), col + c[1]};
                            int[] right = {row + c[0], col + (c[1] + 1)};
                            int[] below = {row + (c[0] + 1), col + c[1]};
                            int[] left = {row + c[0], col + (c[1] - 1)};

                            if (lockedAdjacent.get(matchingType) != null) {
                                for (int[] k : lockedAdjacent.get(matchingType)) {
                                    if (Arrays.equals(k, above)) lockAbove = true;
                                    if (Arrays.equals(k, right)) lockRight = true;
                                    if (Arrays.equals(k, below)) lockBelow = true;
                                    if (Arrays.equals(k, left)) lockLeft = true;
                                }
                            } else {
                                ArrayList<int[]> list = new ArrayList<>();
                                lockedAdjacent.put(matchingType, list);
                            }

                            if (!lockAbove) lockedAdjacent.get(matchingType).add(above);
                            if (!lockRight) lockedAdjacent.get(matchingType).add(right);
                            if (!lockBelow) lockedAdjacent.get(matchingType).add(below);
                            if (!lockLeft) lockedAdjacent.get(matchingType).add(left);
                        }
                    }

                    // add the shape to the map of the found shapes
                    if (!map.containsKey(matchingType)) map.put(matchingType, 1);
                    else map.put(matchingType, map.get(matchingType) + 1);

                } else {
                    // unlock cells
                    for (int[] c : coords) {
                        int[] y = {row + c[0], col + c[1]};
                        locked.get(matchingType).removeIf(x -> Arrays.equals(x, y));
                    }
                }


            }
        }

        return map;
    }

    private int[][] flipHorizontal() {
        int[][] newCoords = coords;
        for (int[] c : newCoords) {
            c[1] = width - 1 - c[1];
        }
        return newCoords;
    }

    private int[][] flipVertical() {
        int[][] newCoords = coords;
        for (int[] c : newCoords) {
            c[0] = height - 1 - c[0];
        }
        return newCoords;
    }

    private int[][] flipDiagonal() {
        int[][] newCoords = coords;
        for (int[] c : newCoords) {
            c[0] = height - 1 - c[0];
            c[1] = width - 1 - c[1];
        }
        return newCoords;
    }

}

