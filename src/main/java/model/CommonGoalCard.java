package model;

public interface CommonGoalCard {
    public boolean checkPattern();
}

class CommonGoalCard1 implements CommonGoalCard {
    public boolean checkPattern(){
        return true;
    }
}
