/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.Player;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author user
 */

class CustomCellView extends ListCell<Player>{
    

    protected void updateItem(Player p, boolean empty) {
        super.updateItem(p, empty); 

        if (empty || p == null) {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText("hello "+p.getName());
        }
    }
    
}

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

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        displayGameRequest("Mustafa");
        onlineList=FXCollections.observableArrayList();
        onlineViewList.setItems(onlineList);
        onlineViewList.setCellFactory(listView->new CustomCellView());
        if(onlineList.isEmpty())
        {
            alertLabel.setVisible(true);
            
           
        }
        
        // TODO
    }    

    @FXML
    private void HomeBtn(MouseEvent event) {
        onlineList.add(new Player("Mustafa"));
        alertLabel.setVisible(false);
        try {
            new SceneController().navigateToHome(event);
        } catch (IOException ex) {
            System.out.println("navgate to home(HomeBTN) exception located in OnlineClientsListController");
        }
    }
    
    

    @FXML
    private void acceptBtnAction(ActionEvent event) {
        try {
            new SceneController().navigateToXOBoard(event);
        } catch (IOException ex) {
            Logger.getLogger(OnlineClientsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void refuseBtnAction(ActionEvent event) {
        gameRequestPane.setVisible(false);
    }
    
    
    public void displayGameRequest(String playerName)
    {
        
        playerRequesting.setText(playerName+" Challenges You");
        progressBar.setProgress(1.0);
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

    
}





