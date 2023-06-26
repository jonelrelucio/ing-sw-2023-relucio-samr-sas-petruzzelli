package it.polimi.ingsw.distributed.events.ViewEvents;

public enum EventView {
    SELECT_COORDINATES_SUCCESS,
    SELECT_COORDINATES_FAIL,
    DESELECT_COORDINATES_SUCCESS,
    DESELECT_COORDINATES_FAIL,
    PICK_TILES_SUCCESS,
    NEW_ORDER_SUCCESS,
    NEW_ORDER_FAIL,
    SELECT_COLUMN_FAIL,
    END_GAME, NEW_TURN,
    UPDATE_CHAT, SHOW_LAST_MESSAGES
}
