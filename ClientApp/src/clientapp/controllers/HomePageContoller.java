/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.GamePlay;
import classes.LocalGamePlay;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author a
 */
public class HomePageContoller implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Button play_VS_ai;
    @FXML
    private Button playOnline;
    @FXML
    private Button playOffline;
    @FXML
    private Button previousMatches;
    @FXML
    private CheckBox recordBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void playWithAi(MouseEvent event) {
        try {
            GamePlay.mode="AI";
            SceneController.navigateToXOBoard(event);
        } catch (IOException ex) {
            Logger.getLogger(HomePageContoller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playOnline(MouseEvent event) {
        try {
            GamePlay.mode="Online"; 
            //new SceneController().navigateToLogin(event);

           new SceneController().navigateToLogin(event);

           //SceneController.navigateToOnlinePlayers(event);
           SceneController.navigateToSignup(event);

        } catch (IOException ex) {
            System.out.println("navgate to playonline Btn exception located in HomeController");;
        }
    }

    @FXML
    private void playOffline(MouseEvent event) {
        try {
            if(recordBtn.isSelected())
            {
                GamePlay.record=true;
            }
            GamePlay.mode="Local";
            SceneController.navigateToXOBoard(event);
        } catch (IOException ex) {
            Logger.getLogger(HomePageContoller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getPreviousMatches(MouseEvent event) {
        try {
            SceneController.navigateToGameRecords(event);
        } catch (IOException ex) {
            Logger.getLogger(HomePageContoller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
