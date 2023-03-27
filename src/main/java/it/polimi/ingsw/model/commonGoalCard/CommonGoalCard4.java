package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

import java.util.HashMap;

public class CommonGoalCard4 implements CommonGoalCard {
    @Override
    public boolean checkPattern(ItemTile[][] bookshelf) {
        HashMap<ItemTileType, Integer> patternControl = new HashMap<>();
        ItemTileType currentType;

        for (int displayRow = bookshelf.length - 1; displayRow >= 0; displayRow--) {
            for (int displayCol = 0; displayCol < bookshelf[0].length; displayCol++) {

                currentType = bookshelf[displayRow][displayCol].getItemTileType();
                if (currentType != ItemTileType.EMPTY) {
                    if (patternControl.containsKey(currentType)) {
                        patternControl.put(currentType, patternControl.get(currentType) + 1);
                    } else {
                        patternControl.put(currentType, 1);
                    }

                    if (patternControl.get(currentType) == 8) return true;
                }
            }
        }

        return false;
    }
}
