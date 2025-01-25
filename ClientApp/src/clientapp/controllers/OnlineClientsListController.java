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
import javafx.scene.layout.Region;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

public class OnlineClientsListController implements Initializable {

    @FXML
    private ListView<Player> onlineViewList;
    ObservableList<Player> onlineList;
    private String invitingPlayer;
    @FXML
    private ImageView logoutBtn;
    @FXML
    private ImageView userInfo;

    private Timeline updateTimeline;
    private boolean isPageVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onlineList = FXCollections.observableArrayList();
        onlineViewList.setItems(onlineList);

        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            if (isPageVisible) {
                ServerLayer.requestOnlinePlayers();
                updateOnlinePlayersList();
            }
        }));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startRefreshing() {
        isPageVisible = true;
        updateTimeline.play();
    }

    public void stopRefreshing() {
        isPageVisible = false;
        updateTimeline.stop();
    }

    private void updateOnlinePlayersList() {
        onlineList.clear();

        for (Player player : ServerLayer.getOnlinePlayersList()) {
            onlineList.add(player);
        }

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
        stopRefreshing(); // Stop refreshing when logging out
        ServerLayer.sendLogoutRequest();
        try {
            SceneController.navigateToHome(event);
        } catch (IOException ex) {
            System.out.println("Navigate to home exception in OnlineClientsListController");
        }
    }

    @FXML
    private void userInfoAction(MouseEvent event) {
        stopRefreshing(); // Stop refreshing when navigating to profile
        try {
            SceneController.navigateToProfile(event);
        } catch (IOException ex) {
            Logger.getLogger(OnlineClientsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}