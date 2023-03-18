package model;

import java.util.HashMap;

public class PersonalGoalCard {


    private static HashMap<Integer, Integer> pointsMapping;
    private ItemTile[][] personalGoal;
    //nel costruttore istanziare pointsMapping e creare la tabella, che sarà uguale per tutte le personalGoalCard

    public PersonalGoalCard() {
        createPointsMapping();
    }

    public void createPointsMapping() {

    }

}
