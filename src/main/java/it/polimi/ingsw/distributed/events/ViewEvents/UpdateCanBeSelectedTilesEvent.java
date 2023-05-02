package it.polimi.ingsw.distributed.events.ViewEvents;


import it.polimi.ingsw.distributed.events.GameEvent;

import java.util.ArrayList;

public class UpdateCanBeSelectedTilesEvent extends GameEvent {

    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<int[]> selectedCoordinates;

    public UpdateCanBeSelectedTilesEvent(ArrayList<int[]> canBeSelectedCoordinates, ArrayList<int[]> selectedCoordinates) {
        super("UPDATE_CAN_BE_SELECTED");
        this.canBeSelectedCoordinates = canBeSelectedCoordinates;
        this.selectedCoordinates = selectedCoordinates;
    }

    public ArrayList<int[]> getCanBeSelectedCoordinates() {
        return canBeSelectedCoordinates;
    }

    public ArrayList<int[]> getSelectedCoordinates() {
        return selectedCoordinates;
    }
}
