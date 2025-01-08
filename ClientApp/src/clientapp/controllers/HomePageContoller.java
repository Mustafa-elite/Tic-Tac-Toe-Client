/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Label userName;
    @FXML
    private ImageView logo;
    @FXML
    private Label logout;
    @FXML
    private ImageView logoutIcon;
    @FXML
    private Button play_VS_ai;
    @FXML
    private Button playOnline;
    @FXML
    private Button playOffline;
    @FXML
    private Button previousMatches;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void playWithAi(MouseEvent event) {
    }

    @FXML
    private void playOnline(MouseEvent event) {
    }

    @FXML
    private void playOffline(MouseEvent event) {
    }

    @FXML
    private void getPreviousMatches(MouseEvent event) {
    }
    
}
