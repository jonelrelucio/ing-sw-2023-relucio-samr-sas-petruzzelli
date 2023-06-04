package it.polimi.ingsw.client.view.gui.guiController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerNplayers implements Initializable {
    @FXML
    private RadioButton FourPlayersButton;
    @FXML
    private RadioButton ThreePlayersButton;
    @FXML
    private RadioButton TwoPlayersButton;
    @FXML
    private Label numberPlayerChosen;
    @FXML
    private Button gotoMainScene;

    private ToggleGroup favLangToggleGroup;
    private int selectedNumber;
    private static ViewGui viewGUI;

    public void setViewGui(ViewGui viewGUI) {
        ControllerNplayers.viewGUI = viewGUI;
    }

    public void goToNextScene(){
        viewGUI.showMain();
    }




    /**
     * This method will update the radioButtonLabel whenever a different
     * radio button is pushed
     */
    public void radioButtonChanged() {
        if (this.favLangToggleGroup.getSelectedToggle().equals(this.TwoPlayersButton)) {
            selectedNumber = 2;
            numberPlayerChosen.setText("The number of players chosen is " + selectedNumber + ".");
        }

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.ThreePlayersButton)) {
            selectedNumber = 3;
            numberPlayerChosen.setText("The number of players chosen is " + selectedNumber + ".");
        }

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.FourPlayersButton)) {
            selectedNumber = 4;
            numberPlayerChosen.setText("The number of players chosen is " + selectedNumber + ".");
        }
    }


    /**
     * setter number of players
     */

    public void setNumberOfPlayers(){
        viewGUI.unLock();
        gotoMainScene.setDisable(false);
    }




    /**
     * getter of selectedNumber
     * @return
     */

    public int getSelectedNumber() {
        return selectedNumber;
    }



    /**
     * initializes the objects before a scene starts
     * @param url
     * @param rb
     */

    public void initialize(URL url, ResourceBundle rb){
        //configuring the number of players selection
        numberPlayerChosen.setText("");
        favLangToggleGroup = new ToggleGroup();
        this.ThreePlayersButton.setToggleGroup(favLangToggleGroup);
        this.TwoPlayersButton.setToggleGroup(favLangToggleGroup);
        this.FourPlayersButton.setToggleGroup(favLangToggleGroup);
        //initialize the button for change scene
        this.gotoMainScene.setDisable(true);
    }

}

