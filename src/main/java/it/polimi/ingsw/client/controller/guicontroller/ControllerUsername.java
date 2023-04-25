package it.polimi.ingsw.client.controller.guicontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * this controller asks the username
 *
 */


public class ControllerUsername implements Initializable {


    @FXML
    TextField nickNameTextField;
    @FXML
    Label nickNameTextLabel;
    @FXML
    Button signInButton;
    @FXML
    Button goToNext;

    private static ViewGui viewGUI;
    private String nickName;

    public void setViewGui(ViewGui viewGUI) {
        ControllerUsername.viewGUI = viewGUI;
    }


    public void goToNextScene(){
        nickNameTextLabel.setText(nickName);
        viewGUI.showMain();
    }


    /**
     * setters
     */


    public String setNickName(){
        nickName = nickNameTextField.getText();
        while(nickName==null){
            nickNameTextLabel.setText("insert a username in the text field above.");
            nickName = nickNameTextField.getText();
        }
        goToNext.setDisable(false);
        return nickName;
    }



    /**
     * getters
     */

    public String getUsername(){
        return nickName;
    }

    /**
     * initializes the objects before a scene starts
     * @param url
     * @param resourceBundle
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        nickNameTextField.setPromptText("userName");
        nickNameTextLabel.setText("");
        goToNext.setDisable(true);
    }
    /**
     * switch to main scene
     *
     */


}


