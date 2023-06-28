package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

public class Const {

    // Tile
    public static final String TILE = "   ";
    public static final String SELECTABLE_TILE = " ¤ ";
    public static final String SELECTED_TILE = " ■ ";


    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[40;30m";  // BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[41;30m";    // RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[42;30m";  // GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[43;30m"; // YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[44;30m";   // BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[45;30m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[46;30m";   // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[47;30m";  // WHITE

    public static final String desc7 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Six groups each containing at least        │
                      │          ■          │  2 tiles of the same type (not necessarily  │
                      │          ■          │  in the depicted shape).                    │
                      │                     │  The tiles of one group can be different    │
                      │          x6         │  from those of another group.               │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                 """;
    public static final String desc8 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Four groups each containing at least       │
                      │         ■           │  4 tiles of the same type (not necessarily  │
                      │         ■    x4     │  in the depicted shape).                    │
                      │         ■           │  The tiles of one group can be different    │
                      │         ■           │  from those of another group.               │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc3 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Two groups each containing 4 tiles of      │
                      │         ■ ■         │  the same type in a 2x2 square. The tiles   │
                      │         ■ ■         │  of one square can be different from        │
                      │                     │  those of the other square.                 │
                      │          x2         │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc2 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Eight tiles of the same type. There’s no   │
                      │        ■   ■        │  restriction about the position of these    │
                      │      ■   ■   ■      │  tiles.                                     │
                      │      ■   ■   ■      │                                             │
                      │                     │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                 """;
    public static final String desc4 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Four tiles of the same type in the four    │
                      │      ■-------■      │  corners of the bookshelf.                  │
                      │      |       |      │                                             │
                      │      |       |      │                                             │
                      │      ■-------■      │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                 """;
    public static final String desc5 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │    ■                │  Five tiles of the same type forming a      │
                      │       ■             │  diagonal.                                  │
                      │          ■          │                                             │
                      │             ■       │                                             │
                      │                ■    │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc1 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │    ■                │  Five columns of increasing or decreasing   │
                      │    ■  ■             │  height. Starting from the first column on  │
                      │    ■  ■  ■          │  the left or on the right, each next column │
                      │    ■  ■  ■  ■       │  must be made of exactly one more tile.     │
                      │    ■  ■  ■  ■  ■    │  Tiles can be of any type.                  │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc6 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Five tiles of the same type forming an X.  │
                      │       ■     ■       │                                             │
                      │          ■          │                                             │
                      │       ■     ■       │                                             │
                      │                     │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc9 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │        ■            │  Three columns each formed by 6 tiles       │
                      │        ■            │  of maximum three different types. One      │
                      │        ■            │  column can show the same or a different    │
                      │        ■     x3     │  combination of another column.             │
                      │        ■            │                                             │
                      │        ■            │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc10 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Four lines each formed by 5 tiles of       │
                      │                     │  maximum three different types. One         │
                      │    ■  ■  ■  ■  ■    │  line can show the same or a different      │
                      │                     │  combination of another line.               │
                      │          x4         │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc11 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │        ■            │  Two columns each formed by 6               │
                      │        ■            │  different types of tiles.                  │
                      │        ■            │                                             │
                      │        ■      x2    │                                             │
                      │        ■            │                                             │
                      │        ■            │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;
    public static final String desc12 = """
                      ┌─────────────────────┬─────────────────────────────────────────────┐
                      │                     │  Two lines each formed by 5 different       │
                      │                     │  types of tiles. One line can show the      │
                      │    ■  ■  ■  ■  ■    │  same or a different combination of the     │
                      │                     │  other line.                                │
                      │          x2         │                                             │
                      │                     │                                             │
                      └─────────────────────┴─────────────────────────────────────────────┘
                """;

    public static String title = """
            Choose a number:\s
            \033[0;32m1\033[0m: New Game
            \033[0;32m2\033[0m: Load Game
            \033[0;32m3\033[0m: Exit
            
            Number:\s""";

    public static String getItemColor(ItemTileType type) {
        return getString(type, PURPLE_BACKGROUND, GREEN_BACKGROUND, YELLOW_BACKGROUND, WHITE_BACKGROUND, RED_BACKGROUND, CYAN_BACKGROUND, BLACK_BACKGROUND);
    }

    public static String getHighlightedItemColor(ItemTileType type) {
        return getString(type, PURPLE_BACKGROUND_BRIGHT, GREEN_BACKGROUND_BRIGHT, YELLOW_BACKGROUND_BRIGHT, WHITE_BACKGROUND_BRIGHT, RED_BACKGROUND_BRIGHT, CYAN_BACKGROUND_BRIGHT, BLACK_BACKGROUND_BRIGHT);
    }

    private static String getString(ItemTileType type, String purple, String green, String yellow, String white, String red, String cyan, String black) {
        return switch (type) {
            case FRAME -> purple;
            case CAT -> green;
            case GAME -> yellow;
            case BOOK -> white;
            case PLANT -> red;
            case TROPHY -> cyan;
            case EMPTY -> black;
        };
    }
}
