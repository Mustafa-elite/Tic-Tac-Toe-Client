/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.GamePlay;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class GameInitializerController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Pane play2Pane;
    @FXML
    private CheckBox recordBtn;
    @FXML
    private Button startBtn;
    @FXML
    private TextField player1TextField;
    @FXML
    private TextField player2TextField;
    @FXML
    private Label warningLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(GamePlay.mode=="AI")
        {
            play2Pane.setVisible(false);
        }
        warningLabel.setVisible(false);
        // TODO
    }    

    @FXML
    private void startBtnAction(ActionEvent event) {
        
        if(player1TextField.getText()=="" || (GamePlay.mode=="Local" && player2TextField.getText()==""))
        {
            warningLabel.setVisible(true);
        }
        else
        {
            try {
                BoardController.player1Name=player1TextField.getText();
                if(GamePlay.mode=="Local")
                {
                    BoardController.player2Name=player2TextField.getText();   
                }
                else
                {
                    BoardController.player2Name= "AI_ROBOT";
                }
                if(recordBtn.isSelected())
                {
                    GamePlay.record=true;
                }

                SceneController.navigateToXOBoard(event);
            } catch (IOException ex) {
                Logger.getLogger(GameInitializerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
