package model.personalGoalCard;

import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileType;

import static model.ItemTile.ItemTileType.*;

public class PersonalGoalCardCoordinates {
    static final int[][][] COORDINATES = {
            { {0,0}, {0,2}, {1,4}, {2,3}, {1,3}, {5,2} },
            { {1,1}, {2,0}, {2,2}, {3,4}, {4,3}, {5,4} },
            { {1,0}, {1,3}, {2,2}, {3,1}, {3,4}, {5,0} },
            { {0,4}, {2,0}, {2,2}, {3,3}, {4,1}, {4,2} },
            { {1,1}, {3,1}, {3,2}, {4,4}, {5,0}, {5,3} },
            { {0,2}, {0,4}, {2,3}, {4,1}, {4,3}, {5,0} },
            { {0,0}, {1,3}, {2,1}, {3,0}, {4,4}, {5,2} },
            { {0,4}, {1,1}, {2,2}, {3,0}, {4,3}, {5,3} },
            { {0,2}, {2,2}, {3,4}, {4,1}, {4,4}, {5,0} },
            { {0,4}, {1,1}, {2,0}, {3,3}, {4,3}, {5,3} },
            { {0,2}, {1,1}, {0,2}, {3,2}, {4,4}, {5,3} },
            { {0,2}, {1,1}, {2,2}, {3,3}, {4,4}, {5,0} }
    };
    static final ItemTileType[][] ITEMCOORDINATES = {
            {PLANT, FRAME, CAT, BOOK, GAME, TROPHY},
            {PLANT, CAT, GAME, BOOK, TROPHY, FRAME},
            {FRAME, GAME, PLANT, CAT, TROPHY, BOOK},
            {GAME, TROPHY, FRAME, PLANT, BOOK, CAT},
            {TROPHY, FRAME, BOOK, PLANT, GAME, CAT},
            {TROPHY, CAT, BOOK, GAME, FRAME, PLANT},
            {CAT, FRAME, PLANT, TROPHY, GAME, BOOK},
            {FRAME, CAT, TROPHY, PLANT, BOOK, GAME},
            {GAME, CAT, BOOK, TROPHY, PLANT, FRAME},
            {TROPHY, GAME, BOOK, CAT, FRAME, PLANT},
            {PLANT, BOOK, GAME, FRAME, CAT, TROPHY},
            {BOOK, PLANT, FRAME, TROPHY, GAME, CAT}
    };

    static public int[][][] getCoordinates() {
        return COORDINATES;
    }

    static public ItemTileType[][] getItemcoordinates() {
        return ITEMCOORDINATES;
    }
}
