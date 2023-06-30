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
public class    CommonGoalSameTypeGroup implements CommonGoalCard{
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

    /**
     * This method scan each element the bookshelf matrix,
     * set the current item tile type that has to be matched 'num' times and call the checkCoords() method and store its return value in the 'result' variable.
     * If 'result' is equal to 'num' then a valid group is found.
     * If 'separated' is true and a valid group is found then call lockAdjacent() method
     * and check if there is an adjacent tile of the same type of the group, if so set the variable 'ok' to false (that indicates that the group is made only of 'num' tiles of the same type and can't be bigger).
     * If a valid group is found and 'ok' is true check if the number of groups found is equal to the 'occurrence' value, if so the pattern if found.
     * Else if a valid group is not found remove the coordinates of its tiles from the 'locked' list and from the 'lockedAdjacent' list.
     * @param bookshelf
     * @return true if the pattern is found
     * @see #checkCoords(ItemTile[][], ItemTileType, HashMap, HashMap, int, int, int, int)
     * @see #lockAdjacent(int, int, ItemTileType, int, HashMap, HashMap)
     * @see #confrontAdjacent(HashMap, int)
     */
    public boolean checkPattern(ItemTile[][] bookshelf) {
        HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> locked = new HashMap<>();
        HashMap<ItemTileType, HashMap<Integer, ArrayList<int[]>>> lockedAdjacent = new HashMap<>();
        int groupID = 0;

        for (int row = 0; row < bookshelf.length; row++) {
            for (int col = 0; col < bookshelf[0].length; col++) {
                ItemTileType matchingType = bookshelf[row][col].getItemTileType();
                if (matchingType != ItemTileType.EMPTY) {
                    groupID++;

                    int result = checkCoords(bookshelf, matchingType, locked, lockedAdjacent, row, col, groupID, 0);

                    boolean ok = true;
                    if (separated && result == num) {
                        lockAdjacent(bookshelf.length, bookshelf[0].length, matchingType, groupID, locked, lockedAdjacent);

                        int[] x = confrontAdjacent(lockedAdjacent.get(matchingType), groupID);
                        if (x != null && bookshelf[x[0]][x[1]].getItemTileType() == matchingType) {
                            ok = false;
                        }
                    }

                    if (result == num && ok) {
                        if (sameType) {
                            for (ItemTileType c : locked.keySet()) {
                                if (locked.get(c).size() == occurrence) {
                                    return true;
                                }
                            }
                        } else {
                            int counter = 0;
                            for (ItemTileType c : locked.keySet()) {
                                counter = counter + locked.get(c).size();
                                if (counter == occurrence) {
                                    return true;
                                }
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
        }

        return false;
    }

    /**
     * This method scans the bookshelf matrix and counts how many adjacent tiles of the same type are found.
     * It calls itself to check the coordinates around the current tile
     * @param bookshelf
     * @param matchingType
     * @param locked
     * @param lockedAdjacent
     * @param row
     * @param col
     * @param groupID
     * @param count
     * @return the number of adjacent tiles of the same type till reaching the 'num' value
     * @see #lock(ItemTileType, int, HashMap, int, int)
     * @see #findCoordsByType(ItemTileType, HashMap, int, int)
     * @see #findCoords(HashMap, int, int)
     */
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

    /**
     * This method check if the coordinates around a tile are available to be locked, if so add them to the 'locked' list.
     * @param height
     * @param width
     * @param matchingType
     * @param groupID
     * @param locked
     * @param lockedAdjacent
     * @see #lock(ItemTileType, int, HashMap, int, int)
     * @see #findCoordsByType(ItemTileType, HashMap, int, int)
     * @see #findCoordsById(HashMap, int, int, int)
     */
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

    /**
     * This method check if the selected group share an adjacent tile with another group
     * @param lockedAdjacent
     * @param groupID
     * @return the coordinates of the first shared adjacent tile encountered, if there is not any match return null
     */
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

    /**
     * This method add the selected coordinates to the 'locked' list
     * @param matchingType
     * @param groupID
     * @param locked
     * @param row
     * @param col
     */
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

    /**
     * This method check if the 'locked' list contains the selected coordinates
     * @param locked
     * @param row
     * @param col
     * @return true if the coordinates are found
     */
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