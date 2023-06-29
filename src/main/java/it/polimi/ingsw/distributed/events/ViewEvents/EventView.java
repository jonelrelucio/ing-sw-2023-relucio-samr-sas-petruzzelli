package it.polimi.ingsw.distributed.events.ViewEvents;

/**
 * This enum represents all the events that the client could be notified of and has to handle
 */
public enum EventView {
    SELECT_COORDINATES_SUCCESS,
    SELECT_COORDINATES_FAIL,
    DESELECT_COORDINATES_SUCCESS,
    DESELECT_COORDINATES_FAIL,
    PICK_TILES_SUCCESS,
    NEW_ORDER_SUCCESS,
    NEW_ORDER_FAIL,
    SELECT_COLUMN_FAIL,
    END_GAME,
    NEW_TURN,
    UPDATE_CHAT,
    SHOW_LAST_MESSAGES,
    UPDATE_PRIVATE_CHAT
}
