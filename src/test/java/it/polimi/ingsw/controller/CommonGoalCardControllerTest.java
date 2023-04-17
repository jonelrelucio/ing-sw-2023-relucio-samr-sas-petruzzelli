package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCardControllerTest {

    GameModel testModel = new GameModel(3);
    CommonGoalCardController testController = new CommonGoalCardController(testModel);

    @Test
    void checkCommonGoal() {
    }

}