/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.Player;
import classes.ServerLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author user
 */

public class OnlineClientsListController implements Initializable {

    @FXML
    private ListView<Player> onlineViewList;
    @FXML
    private Label alertLabel;
    
    ObservableList<Player> onlineList;
    @FXML
    private Button sendRequest;
    private String invitingPlayer;
    @FXML
    private Button refresh;
    @FXML
    private ImageView logoutBtn;
    @FXML
    private ImageView userInfo;


    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //displayGameRequest("Mustafa");
        
        
        onlineList=FXCollections.observableArrayList();
        onlineViewList.setItems(onlineList);
        ServerLayer.requestOnlinePlayers();
        if(onlineList.isEmpty())
        {
            alertLabel.setVisible(true);
            
           
        }
            
       
    }    

    private void HomeBtn(MouseEvent event) {
        //onlineList.add(new Player("Mustafa"));
        
        
    }
    
    
   
    
    private void refreshOnlinePlayers(ActionEvent event) {
        ServerLayer.requestOnlinePlayers(); 
    }
    
    
    
    //////////////////////////implemented for testing sending request to ahmed////////////////////////////////////////
    @FXML
    private void sendingRequestBtn(ActionEvent event) {
        ServerLayer.sendPlayRequest("ahmed");
    }

    @FXML
    private void refreshList(ActionEvent event) {
        ServerLayer.requestOnlinePlayers();
        onlineViewList.setItems(ServerLayer.getOnlinePlayersList());
       onlineViewList.setCellFactory(listView -> new ListCell<Player>() {
           @Override
           protected void updateItem(Player player, boolean empty) {
               super.updateItem(player, empty);
               if (empty || player == null) {
                   setText(null);
               } else {
                   
                    Button challengeButton = new Button();
                    if (player.isAvailable()) {
                        challengeButton.setText("Challenge");
                        challengeButton.setDisable(false);
                    } else {
                        challengeButton.setText("Not Available");
                        challengeButton.setDisable(true);
                    }

                  
                    challengeButton.setOnAction(e -> {
                        System.out.println("Challenging " + player.getName());
                        ServerLayer.sendPlayRequest(player.getName());
                    });

                    
                    HBox hbox = new HBox(10); 
                    hbox.getChildren().addAll(new Label(player.getName()), challengeButton);
                    setGraphic(hbox);
                }
            }
        });
    }

    @FXML
    private void logoutAction(MouseEvent event) {
        ServerLayer.sendLogoutRequest();
        try {
            SceneController.navigateToHome(event);
        } catch (IOException ex) {
            System.out.println("navgate to home(HomeBTN) exception located in OnlineClientsListController");
        }
    }

    @FXML
    private void userInfoAction(MouseEvent event) {
        try {
            SceneController.navigateToProfile(event);
        } catch (IOException ex) {
            Logger.getLogger(OnlineClientsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}





