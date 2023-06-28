package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class represents a type of common goal card that has a defined shape and occurrence
 */
public class CommonGoalShape implements CommonGoalCard{
    /**
     * Unique card's id
     */
    private final int id;
    /**
     * Width of the matrix containing the shape
     */
    private final int width;
    /**
     * Height of the matrix containing the shape
     */
    private final int height;
    /**
     * Coordinates of the cell that have to match their type
     */
    private final int[][] coords;
    /**
     * Occurrences of the shape to be found
     */
    private final int occurrence;
    /**
     * Flag that indicates to check if there is the shape flipped horizontally
     */
    private final boolean verticalSymmetric;
    /**
     * Flag that indicates to check if there is the shape flipped vertically
     */
    private final boolean horizontalSymmetric;
    /**
     * Flag that indicates to check if there is the shape flipped diagonally
     */
    private final boolean diagonalSymmetric;
    /**
     * Flag that indicates to check if the shape is made of the same tile type
     */
    private final boolean groupsOfSameType;
    /**
     * Flag that indicates to check if the occurrences have to not be adjacent
     */
    private final boolean separated;

    /**
     * Initialize the card
     * @param id
     * @param width
     * @param height
     * @param coords
     * @param occurrence
     * @param verticalSymmetric
     * @param horizontalSymmetric
     * @param diagonalSymmetric
     * @param groupsOfSameType
     * @param separated
     */
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

    /**
     * The method check if the card's shape is present with at least one transformation based on the corresponding field value:
     * no transformation, flipped vertical, flipped horizontal, flipped diagonally
     * @param bookshelf
     * @return true if the shape is found
     * @see #findShape(ItemTile[][], int[][], HashMap, HashMap)
     * @see #flipDiagonal()
     * @see #flipVertical()
     * @see #flipHorizontal()
     */
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

    /**
     * This method inspect the bookshelf matrix and fill a hashmap with the tiles type and their occurrences.
     * Fill the hashmaps 'locked' and 'lockedAdjacent' with the tiles to not inspect
     * @param bookshelf
     * @param coords
     * @param locked
     * @param lockedAdjacent
     * @return the hashmap with tile's type as keys and the occurrences of the tiles as value
     */
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

    /**
     * This method flip horizontally the coordinates of coords field
     * @return the new coordinates
     */
    private int[][] flipHorizontal() {
        int[][] newCoords = coords;
        for (int[] c : newCoords) {
            c[1] = width - 1 - c[1];
        }
        return newCoords;
    }

    /**
     * This method flip vertically the coordinates of coords field
     * @return the new coordinates
     */
    private int[][] flipVertical() {
        int[][] newCoords = coords;
        for (int[] c : newCoords) {
            c[0] = height - 1 - c[0];
        }
        return newCoords;
    }

    /**
     * This method flip diagonally the coordinates of coords field
     * @return the new coordinates
     */
    private int[][] flipDiagonal() {
        int[][] newCoords = coords;
        for (int[] c : newCoords) {
            c[0] = height - 1 - c[0];
            c[1] = width - 1 - c[1];
        }
        return newCoords;
    }

    /**
     * Getter for 'id' field
     * @return the card's id
     */
    public int getId() { return id; }
}

