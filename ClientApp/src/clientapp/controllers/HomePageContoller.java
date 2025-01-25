/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.GamePlay;
import classes.LocalGamePlay;
import classes.ServerLayer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

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

            GamePlay.mode="Online"; 
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
        String portText = portField.getText().trim();
        if (!isValidIP(ipAddress)) {
            showAlert("Invalid IP Address", "Please enter a valid IP address (e.g., 127.0.0.1).");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Port", "Please enter a valid port number (e.g., 5005).");
            return;
        }
        if (!isValidPort(port)) {
            showAlert("Invalid Port", "Please enter a valid port number (0-65535).");
            return;
        }
        try (Socket testSocket = new Socket(ipAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
            PrintWriter out = new PrintWriter(testSocket.getOutputStream(), true)) {
            String serverResponse = in.readLine();
            JsonReader jsonReader = Json.createReader(new StringReader(serverResponse));
            JsonObject jsonObject = jsonReader.readObject();


            if (!"authToken".equals(jsonObject.getString("Header")) ||
                !"TicTacToeServer".equals(jsonObject.getString("token"))) {
                showAlert("Invalid Server", "The server is not recognized. Please connect to the correct server.");
                return;
            }
            ServerLayer.setServerIP(ipAddress);
            ServerLayer.setServerPort(port);
            SceneController.navigateToLogin(event);
        }catch (IOException e) {
            showAlert("Connection Failed", "Unable to connect to the server. Please check the IP address and port.");
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
     private boolean isValidIP(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPort(int port) {
        return port >= 0 && port <= 65535;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}