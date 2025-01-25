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
import javafx.application.Platform;
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
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author user
 */
public class OnlineClientsListController implements Initializable {

    @FXML
    private ListView<Player> onlineViewList;
    ObservableList<Player> onlineList;
    private String invitingPlayer;
    @FXML
    private Button refresh;
    @FXML
    private ImageView logoutBtn;
    @FXML
    private ImageView userInfo;

    private Timeline updateTimeline;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        ServerLayer.requestOnlinePlayers();
   
        /*//start my 7mosany code
        onlineViewList.setItems(ServerLayer.getOnlinePlayersList());

        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {

            refreshList(event);
        }));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        //updateTimeline.play();
        
         final Timeline[] waitForScene = new Timeline[1];

        waitForScene[0] = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (onlineViewList.getScene() != null && onlineViewList.getScene().getWindow() != null) {
                // Scene and window are now available
                System.out.println("Scene and window are now set");

                // Add listener for window focus changes
                onlineViewList.getScene().getWindow().focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                    if (isFocused) {
                        System.out.println("Scene is focused");
                        updateTimeline.play(); // Start updates
                    } else {
                        System.out.println("Scene is not focused");
                        updateTimeline.stop(); // Stop updates
                    }
                });

                // Stop the waitForScene Timeline
                waitForScene[0].stop();
            }
        }));
        waitForScene[0].setCycleCount(Timeline.INDEFINITE); // Keep checking until the scene is ready
        waitForScene[0].play(); // Start checking
        //end of my 7mosany code
*/
    }

    @FXML
    private void refreshList(ActionEvent event) {
        ServerLayer.requestOnlinePlayers();
        onlineViewList.setItems(ServerLayer.getOnlinePlayersList());
        onlineViewList.setStyle("-fx-padding: 10 0 0 0;");
        onlineViewList.setCellFactory(listView -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label playerNameLabel = new Label(player.getName());
                    playerNameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff;");

                    Button challengeButton = new Button();
                    challengeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 20;-fx-font-size: 14px");

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

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    HBox hbox = new HBox(9);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setStyle("-fx-padding: 10; -fx-background-color: #20608B; -fx-background-radius: 10;");

                    hbox.getChildren().addAll(playerNameLabel, spacer, challengeButton);
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

    public void stopUpdatingOnlineList()
    {
        updateTimeline.stop();
    }
}
