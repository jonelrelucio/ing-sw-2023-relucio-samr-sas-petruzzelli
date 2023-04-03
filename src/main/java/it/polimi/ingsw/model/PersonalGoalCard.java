package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ItemTile.ItemTile;
public class PersonalGoalCard {

    private ItemTile[][] personalGoalMatrix;

    public ItemTile[][] getPersonalGoalCardMatrix() { return personalGoalMatrix; }
    public void setPersonalGoalMatrix(ItemTile[][] personalGoalMatrix) { this.personalGoalMatrix = personalGoalMatrix; }

    // TODO: remove print personal goal
    public void printPersonalGoal() {
        for (ItemTile[] itemTiles : personalGoalMatrix) {
            for (int k = 0; k < personalGoalMatrix[0].length; k++) {
                System.out.printf("%10s", itemTiles[k].getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }


}

