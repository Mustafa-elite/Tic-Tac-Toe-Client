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
    private ImageView homePage;
    @FXML
    private ListView<Player> onlineViewList;
    @FXML
    private Label alertLabel;
    
    ObservableList<Player> onlineList;
    @FXML
    private Pane gameRequestPane;
    @FXML
    private Label playerRequesting;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button sendRequest;
    private String invitingPlayer;
    @FXML
    private Button refresh;


    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //displayGameRequest("Mustafa");
        ServerLayer.setonlineController(this);
        gameRequestPane.setVisible(false);
        onlineList=FXCollections.observableArrayList();
        onlineViewList.setItems(onlineList);
        ServerLayer.requestOnlinePlayers();
        if(onlineList.isEmpty())
        {
            alertLabel.setVisible(true);
            
           
        }
            
       
    }    

    @FXML
    private void HomeBtn(MouseEvent event) {
        onlineList.add(new Player("Mustafa"));
        alertLabel.setVisible(false);
        try {
            SceneController.navigateToHome(event);
        } catch (IOException ex) {
            System.out.println("navgate to home(HomeBTN) exception located in OnlineClientsListController");
        }
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
    
    private void refreshOnlinePlayers(ActionEvent event) {
        ServerLayer.requestOnlinePlayers(); 
    }
    
    
    public  void displayGameRequest(String playerName)
    {
        invitingPlayer=playerName;
        playerRequesting.setText(playerName+" Challenges You");
        progressBar.setProgress(1.0);
        gameRequestPane.setVisible(true);
        int time2display = 5;
        KeyFrame updatingFrame= new KeyFrame(Duration.millis(100), event -> {
                double progress=progressBar.getProgress();
                double newProgress=Math.max(0,progress-0.1/time2display);
                progressBar.setProgress(newProgress);
        });
        Timeline timeline = new Timeline(updatingFrame);
        
        
        timeline.setCycleCount(time2display * 10); 
        timeline.setOnFinished(event -> gameRequestPane.setVisible(false));
        timeline.play();
    
    
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
    

    
}





