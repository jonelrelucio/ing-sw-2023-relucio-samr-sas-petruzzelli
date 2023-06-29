package it.polimi.ingsw.distributed.events.controllerEvents;

/**
 * This enum represents all the events that the controller could be notified of and has to handle
 */
public enum EventController {
    SELECT_COORDINATES,
    DESELECT_COORDINATES,
    NEW_ORDER,
    SELECT_COLUMN,
    PICK_TILES,
    SHOW_CHAT,
    NEW_MESSAGE_CHAT,
    NEW_MESSAGE_TO
}
