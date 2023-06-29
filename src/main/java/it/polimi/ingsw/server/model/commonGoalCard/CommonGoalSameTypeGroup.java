package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class represents a type of common goal card that not has a defined shape
 * but has a defined occurrence value
 */
public class CommonGoalSameTypeGroup implements CommonGoalCard{
    /**
     * Unique card's id
     */
    int id;
    /**
     * Number of tiles in the group
     */
    int num;
    /**
     * Occurrences of the shape to be found
     */
    int occurrence;
    /**
     * Flag that indicates to check if the occurrences have to not be adjacent
     */
    boolean separated;
    /**
     * Flag that indicates to check if the groups have to be of the same tile type
     */
    boolean sameType;

    /**
     * Initialize the card
     * @param id
     * @param num
     * @param occurrence
     * @param separated
     * @param sameType
     */
    public CommonGoalSameTypeGroup(int id, int num, int occurrence, boolean separated, boolean sameType) {
        this.id = id;
        this.num = num;
        this.occurrence = occurrence;
        this.separated = separated;
        this.sameType = sameType;
    }

    public boolean checkPattern(ItemTile[][] bookshelf) {
        HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked = new HashMap<>();
        HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> lockedAdjacent = new HashMap<>();
        int groupID = 0;

        for (int row = 0; row < bookshelf.length; row++) {
            for (int col = 0; col < bookshelf[0].length; col++) {
                ItemTileType matchingType = bookshelf[row][col].getItemTileType();
                groupID++;

                int result = checkCoords(bookshelf, matchingType, locked, lockedAdjacent, row, col, groupID, 0);

                boolean ok = true;
                if (separated && result == num) {
                    lockAdjacent(bookshelf.length, bookshelf[0].length, matchingType, groupID, locked, lockedAdjacent);

                    int[] x = confrontAdjacent(lockedAdjacent.get(matchingType), groupID);
                    if (x != null && bookshelf[x[0]][x[1]].getItemTileType() == matchingType) ok = false;
                }

                if (result == num && ok) {
                    if (sameType) {
                        for (ItemTileType c : locked.keySet()) {
                            if (locked.get(c).size() == occurrence) return true;
                        }
                    } else {
                        int counter = 0;
                        for (ItemTileType c : locked.keySet()) {
                            counter = counter + locked.get(c).size();
                            if (counter == occurrence) return true;
                        }
                    }

                }

                if (result != num || !ok) {
                    // unlock the not valid group cells
                    if (locked.containsKey(matchingType) && locked.get(matchingType).containsKey(groupID)) {
                        locked.get(matchingType).get(groupID).clear();
                        locked.get(matchingType).remove(groupID);
                    }
                    if (locked.get(matchingType) != null && locked.get(matchingType).isEmpty()) locked.remove(matchingType);

                    if (separated) {
                        if (lockedAdjacent.containsKey(matchingType) && lockedAdjacent.get(matchingType).containsKey(groupID)) {
                            lockedAdjacent.get(matchingType).get(groupID).clear();
                            lockedAdjacent.get(matchingType).remove(groupID);
                        }
                        if (lockedAdjacent.get(matchingType) != null && lockedAdjacent.get(matchingType).isEmpty()) lockedAdjacent.remove(matchingType);
                    }
                }
            }
        }

        return false;
    }


    private int checkCoords(ItemTile[][] bookshelf, ItemTileType matchingType, HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked, HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> lockedAdjacent, int row, int col, int groupID, int count) {
        if (separated && sameType) {
            if (bookshelf[row][col].getItemTileType() == matchingType && findCoordsByType(matchingType, lockedAdjacent, row, col)) return count;
        } else if (separated) {
            if (findCoords(lockedAdjacent, row, col)) return count;
        }

        if (!findCoords(locked, row, col) && bookshelf[row][col].getItemTileType() == matchingType) {
            // add to locked
            lock(matchingType, groupID, locked, row, col);

            // increase count and check if is == num
            count++;
            if (count == num) {
                return count;
            }

            // check adjacent cells
            // check above
            if (row - 1 >= 0) {
                count = checkCoords(bookshelf, matchingType, locked, lockedAdjacent, row - 1, col, groupID, count);
                if (count == num) {
                    return count;
                }
            }

            // check left
            if (col - 1 >= 0) {
                count = checkCoords(bookshelf, matchingType, locked, lockedAdjacent, row, col - 1, groupID, count);
                if (count == num) {
                    return count;
                }
            }

            // check below
            if (row + 1 < bookshelf.length) {
                count = checkCoords(bookshelf, matchingType, locked, lockedAdjacent, row + 1, col, groupID, count);
                if (count == num) {
                    return count;
                }
            }

            // check right
            if (col + 1 < bookshelf[0].length) {
                count = checkCoords(bookshelf, matchingType, locked, lockedAdjacent, row, col + 1, groupID, count);
                if (count == num) {
                    return count;
                }
            }
        }

        return count;
    }


    private void lockAdjacent(int height, int width, ItemTileType matchingType, int groupID, HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked, HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> lockedAdjacent) {
        int row, col;
        for (int[] x : locked.get(matchingType).get(groupID)) {
            row = x[0];
            col = x[1];
            // above
            if (row - 1 >= 0 && !findCoordsByType(matchingType, locked, row - 1, col) && !findCoordsById(lockedAdjacent.get(matchingType), row - 1, col, groupID)) {
                lock(matchingType, groupID, lockedAdjacent, row - 1, col);
            }

            // right
            if (col + 1 < width && !findCoordsByType(matchingType, locked, row, col + 1) && !findCoordsById(lockedAdjacent.get(matchingType), row, col + 1, groupID)) {
                lock(matchingType, groupID, lockedAdjacent, row, col + 1);
            }

            // below
            if (row + 1 < height && !findCoordsByType(matchingType, locked, row + 1, col) && !findCoordsById(lockedAdjacent.get(matchingType), row + 1, col, groupID)) {
                lock(matchingType, groupID, lockedAdjacent, row + 1, col);
            }

            // left
            if (col - 1 >= 0 && !findCoordsByType(matchingType, locked, row, col - 1) && !findCoordsById(lockedAdjacent.get(matchingType), row, col - 1, groupID)) {
                lock(matchingType, groupID, lockedAdjacent, row, col - 1);
            }
        }
    }


    private int[] confrontAdjacent(HashMap<Integer, ArrayList<int[]>> lockedAdjacent, int groupID) {
        if (lockedAdjacent != null && lockedAdjacent.containsKey(groupID)) {
            for (Integer id : lockedAdjacent.keySet()) {
                if (id != groupID) {
                    for (int[] x : lockedAdjacent.get(id)) {
                        for (int[] y : lockedAdjacent.get(groupID)) {
                            if (Arrays.equals(x, y)) return x;
                        }
                    }
                }
            }
        }
        return null;
    }


    private void lock(ItemTileType matchingType, int groupID, HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked, int row, int col) {
        if (locked.containsKey(matchingType)) {
            if (locked.get(matchingType).containsKey(groupID)) locked.get(matchingType).get(groupID).add(new int[]{row, col});
            else {
                ArrayList<int[]> list1 = new ArrayList<>();
                list1.add(new int[]{row, col});
                locked.get(matchingType).put(groupID, list1);
            }
        } else {
            HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();
            ArrayList<int[]> list2 = new ArrayList<>();
            list2.add(new int[]{row, col});
            map.put(groupID, list2);
            locked.put(matchingType, map);
        }
    }


    private boolean findCoords(HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked, int row, int col) {
        for (ItemTileType c : locked.keySet()) {
            for (Integer i : locked.get(c).keySet()) {
                for (int[] k : locked.get(c).get(i)) {
                    if (Arrays.equals(k, new int[]{row, col})) return true;
                }
            }
        }

        return false;
    }

    /**
     * This method check if the current coordinates tile is present in the 'locked' ArrayList by its type
     * @param matchingType
     * @param locked
     * @param row
     * @param col
     * @return
     */
    private boolean findCoordsByType(ItemTileType matchingType, HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked, int row, int col) {
        if (locked.get(matchingType) != null) {
            for (Integer i : locked.get(matchingType).keySet()) {
                for (int[] k : locked.get(matchingType).get(i)) {
                    if (Arrays.equals(k, new int[]{row, col})) return true;
                }
            }
        }
        return false;
    }

    /**
     * This method check if the current coordinates are present in the 'locked' ArrayList of the selected group
     * @param locked
     * @param row
     * @param col
     * @param groupID
     * @return if the coordinates are present in the 'locked' ArrayList
     */
    private boolean findCoordsById(HashMap<Integer, ArrayList<int[]>> locked, int row, int col, int groupID) {
        if (locked != null && locked.containsKey(groupID)) {
            for (int[] x : locked.get(groupID)) {
                if (Arrays.equals(x, new int[]{row, col})) return true;
            }
        }

        return false;
    }

    /**
     * Getter for 'id' field
     * @return the card's id
     */
    public int getId() { return id; }
}