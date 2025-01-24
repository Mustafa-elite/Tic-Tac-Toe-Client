/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.ServerLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author user
 */
public class GameRequestController implements Initializable {

    @FXML
    private Pane gameRequestPane;
    @FXML
    private Label playerRequesting;
    @FXML
    private ProgressBar progressBar;
    private String invitingPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void acceptBtnAction(ActionEvent event) {
        ServerLayer.sendGameAcceptance(invitingPlayer);
        try {
            SceneController.navigateToXOBoard(event);
        } catch (IOException ex) {
           System.out.println("error navigating to XO Gui Screen after clicking accept btn");
        }
    }

    @FXML
    private void refuseBtnAction(ActionEvent event) {
        gameRequestPane.setVisible(false);
        
    }
     public  void displayGameRequest(String playerName)
    {
        invitingPlayer=playerName;
        playerRequesting.setText(playerName+" Challenges You");
        progressBar.setProgress(1.0);
        //gameRequestPane.setVisible(true);
        int time2display = 5;
        KeyFrame updatingFrame= new KeyFrame(Duration.millis(100), event -> {
                double progress=progressBar.getProgress();
                double newProgress=Math.max(0,progress-0.1/time2display);
                progressBar.setProgress(newProgress);
        });
        Timeline timeline = new Timeline(updatingFrame);
        
        
        timeline.setCycleCount(time2display * 10); 
        timeline.setOnFinished(event -> 
        {
            gameRequestPane.setVisible(false);
            
        });
        timeline.play();
    
    
    }
}
