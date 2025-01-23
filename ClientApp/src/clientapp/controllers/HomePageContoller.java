/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.GamePlay;
import classes.LocalGamePlay;
import classes.ServerLayer;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    private Pane serverInputPane;
    @FXML
    private TextField ipAddressField;
    @FXML
    private TextField portField;
    @FXML
    private Button connectButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          serverInputPane.setVisible(false);
    }

    @FXML
    private void playWithAi(MouseEvent event) {
        try {
            GamePlay.mode="AI";
            SceneController.navigateGameInitializer(event);
        } catch (IOException ex) {
            Logger.getLogger(HomePageContoller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playOnline(MouseEvent event) {
       
           serverInputPane.setVisible(true);
           disableButtons(true);
 
    }

    @FXML
    private void playOffline(MouseEvent event) {
        try {

            GamePlay.mode="Local";
            SceneController.navigateGameInitializer(event);
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
    @FXML
    private void connectToServer(ActionEvent event) {
        String ipAddress = ipAddressField.getText();
        int port = Integer.parseInt(portField.getText());

        // Try to connect to the server
        try {
            ServerLayer.setServerIP(ipAddress);
            ServerLayer.setServerPort(port);
            SceneController.navigateToLogin(event);
        } catch (Exception e) {
            System.out.println("Failed to connect to the server: " + e.getMessage());
        } finally {
            
            serverInputPane.setVisible(false);
            disableButtons(false);
        }
    }
    @FXML
    private void cancelConnect(ActionEvent event) {
        // Hide the server input pane and re-enable buttons
        serverInputPane.setVisible(false);
        disableButtons(false);
    }
     private void disableButtons(boolean disable) {
        play_VS_ai.setDisable(disable);
        playOnline.setDisable(disable);
        playOffline.setDisable(disable);
        previousMatches.setDisable(disable);
    }

}