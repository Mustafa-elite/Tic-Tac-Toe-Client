/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.AIGamePlay;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import classes.GamePlay;
import classes.GameStatus;
import classes.LocalGamePlay;
import classes.ServerLayer;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import classes.OnlineGamePlay;
import static classes.ServerLayer.invitingFlag;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author khali
 */
public class BoardController implements Initializable {

    GamePlay XO;
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    @FXML
    private Button Button5;
    @FXML
    private Button Button4;
    @FXML
    private Button Button7;
    @FXML
    private Button Button6;
    @FXML
    private Button Button8;
    @FXML
    private Button Button9;
    @FXML
    private Button Button1;
    @FXML
    private Button Button3;
    @FXML
    private Button Button2;
    @FXML
    private Line Row1;
    @FXML
    private Line Row2;
    @FXML
    private Line Col1;
    @FXML
    private Line Col2;
    @FXML
    private Line Col3;
    @FXML
    private Line Diagonal1;
    @FXML
    private Line Diagonal2;
    @FXML
    private Line Row3;
    @FXML
    private Label ResultLabel;
    @FXML
    private Pane ResultPane;
    private Button[] btnArr;
    @FXML
    private Label player1Label;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Label;
    @FXML
    private Label player2Score;
    @FXML
    private Pane player2Pane;
    private String playerPaneColor = "-fx-background-color: #7eff7e;";
    @FXML
    private Pane player1Pane;

    public static String player1Name;
    public static String player2Name;
    public static boolean isreplay;
    public static ArrayList<String> gameReplay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ServerLayer.setBoredConrtoller(this);
        btnArr = new Button[]{Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9};
        if (GamePlay.mode == "AI") {
            XO = new AIGamePlay(player1Name, player2Name);
            if (!XO.isTurn()) {

                handleButtonClick(null);
            } else {
                player2Pane.setStyle("-fx-background-color: #7eff7e;");
            }
        } else if (GamePlay.mode == "Local") {

            if (gameReplay != null) {
                //it is not a real match,it is a replay
                player1Name = gameReplay.remove(0);
                player2Name = gameReplay.remove(0);
                //XO.setTurn(gameReplay.remove(0)=="1" ? true:false);

                XO = new LocalGamePlay(player1Name, player2Name);
                XO.setTurn("1".equals(gameReplay.remove(0)));
                handleButtonClick(null);
                disableAllBtns();

            } else {
                XO = new LocalGamePlay(player1Name, player2Name);

            }

        } else if (GamePlay.mode == "Online") {
            //parameters of online gameplay should be sent from acccept button(client 2) in OnlineClientListContoller and from receivegameacceptance(client 1)

            if (!ServerLayer.invitingFlag) {
                disableAllBtns();
                player1Name = ServerLayer.getOpponentPlayer().getName();
                player1Score.setText(String.valueOf(ServerLayer.getOpponentPlayer().getScore()));
                player2Name = ServerLayer.getMyPlayer().getName();
                player2Score.setText(String.valueOf(ServerLayer.getMyPlayer().getScore()));

            } else {
                player1Name = ServerLayer.getMyPlayer().getName();
                player1Score.setText(String.valueOf(ServerLayer.getMyPlayer().getScore()));
                player2Name = ServerLayer.getOpponentPlayer().getName();
                player2Score.setText(String.valueOf(ServerLayer.getOpponentPlayer().getScore()));
            }
            XO = new OnlineGamePlay(player1Name, player2Name);
            XO.setTurn(true);

        }

        if (XO.isTurn()) {
            player1Pane.setStyle(playerPaneColor);
        } else {
            player2Pane.setStyle(playerPaneColor);
        }
        player1Label.setText(player1Name);
        player2Label.setText(player2Name);

    }

    private void showResult(String result) {
        ResultLabel.setText(result);
        ResultPane.setVisible(true);

        // Determine the video path based on the result
        String videoPath;
        if (result.contains("won")) {
            videoPath = "/clientapp/assets/Won.mp4";
        } else if (result.contains("lost")) {
            videoPath = "/clientapp/assets/Lost.mp4";
        } else if (result.equals("Draw")) {
            videoPath = "/clientapp/assets/Draw.mp4";
        } else {
            videoPath = ""; // No video for other cases
        }

        // Debug: Print the video path
        System.out.println("Video Path: " + videoPath);

        // Load and play the video
        if (!videoPath.isEmpty()) {
            try {
                // Use getClass().getResource() to load the video as a resource
                Media media = new Media(getClass().getResource(videoPath).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);

                // Debug: Print media properties
                mediaPlayer.setOnReady(() -> {
                    System.out.println("Media is ready to play.");
                });

                mediaPlayer.setOnError(() -> {
                    System.out.println("Media error: " + mediaPlayer.getError().getMessage());
                });

                // Play the video
                mediaPlayer.play();
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaView.setVisible(false);
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.dispose();
                        mediaPlayer = null;
                    }
                });
                //ResultPane.setVisible(false);
            } catch (Exception e) {
                System.out.println("Error loading video: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (GamePlay.mode == "AI") {
            AiGameEvent(event);

        } else if (GamePlay.mode == "Local") {
            localGameEvent(event);
        } else if (GamePlay.mode == "Online") {
            onlineGamePlay(event);
        }
    }

    private void AiGameEvent(ActionEvent event) {
        int buttonNo = -1;
        Button clickedButton = null;
        if (XO.isTurn()) {
            clickedButton = (Button) event.getSource();
            for (int i = 0; i < 9; i++) {
                if (clickedButton == btnArr[i]) {
                    buttonNo = i;
                    break;
                }
            }
        }
        GameStatus status = XO.playXO(buttonNo);

        if (XO.isTurn()) {
            clickedButton.setText(status.getPlayedChar());
            clickedButton.setDisable(true);
        } else {
            btnArr[status.getPosition()].setText("X");
            btnArr[status.getPosition()].setDisable(true);
        }

        if (status.getWinnerName() != null) {
            String result = status.getWinnerName().equals(player1Name) ? "You won!" : "You lost!";
            drawWinnerLine(status.getWinCase());
            showResult(result);
        } else if (status.isDraw()) {
            showResult("Draw");
        }
        if (XO.isTurn()) {
            XO.setTurn(false);
            player2Pane.setStyle(playerPaneColor);
            player1Pane.setStyle("");
            ////there should be delay here
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> handleButtonClick(null));
            pause.play();

        } else {
            player1Pane.setStyle(playerPaneColor);
            player2Pane.setStyle("");
            XO.setTurn(true);
        }

    }

    private void localGameEvent(ActionEvent event) {
        int buttonNo = -1;
        Button clickedButton = null;

        if (event != null) {
            clickedButton = (Button) event.getSource();

            for (int i = 0; i < 9; i++) {
                if (clickedButton == btnArr[i]) {
                    buttonNo = i;
                    break;
                }
            }
        } else {
            buttonNo = Integer.parseInt(gameReplay.remove(0));
            if (gameReplay.isEmpty()) {
                gameReplay = null;
            }
        }

        GameStatus status = XO.playXO(buttonNo);

        if (event != null) {
            clickedButton.setText(status.getPlayedChar());
            clickedButton.setDisable(true);
        } else {
            btnArr[status.getPosition()].setText(status.getPlayedChar());
            btnArr[status.getPosition()].setDisable(true);
        }

        if (status.getWinnerName() != null) {
            String result = status.getWinnerName().equals(player1Name) ? player1Name + " won !" : player2Name + " won !";
            drawWinnerLine(status.getWinCase());
/////////////delay
            showResult(result);
        } else if (status.isDraw()) {
            /////////delay
            showResult("Draw");
        }
        if (XO.isTurn()) {
            player2Pane.setStyle(playerPaneColor);
            player1Pane.setStyle("");
            XO.setTurn(false);

        } else {
            player1Pane.setStyle(playerPaneColor);
            player2Pane.setStyle("");

            XO.setTurn(true);
        }
        if (event == null) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> handleButtonClick(null));
            pause.play();
        }

    }

    private void drawWinnerLine(int winCase) {

        switch (winCase) {
            case GamePlay.WIN_ROW_1: {
                Row1.setVisible(true);
                break;
            }
            case GamePlay.WIN_ROW_2: {
                Row2.setVisible(true);

                break;
            }
            case GamePlay.WIN_ROW_3: {
                Row3.setVisible(true);

                break;
            }
            case GamePlay.WIN_COL_1: {
                Col1.setVisible(true);

                break;
            }
            case GamePlay.WIN_COL_2: {
                Col2.setVisible(true);

                break;
            }
            case GamePlay.WIN_COL_3: {
                Col3.setVisible(true);

                break;
            }
            case GamePlay.WIN_DIAGONAL_1: {
                Diagonal1.setVisible(true);

                break;
            }
            case GamePlay.WIN_DIAGONAL_2: {
                Diagonal2.setVisible(true);

                break;
            }

        }
    }

    private void onlineGamePlay(ActionEvent event) {

        int buttonNumber = -1;
        String winnerName = null;
        Button buttonClicked = new Button();
        if (event != null) {
            buttonClicked = (Button) event.getSource();
            System.out.println("event not null  " + XO.isTurn());
            for (int i = 0; i < 9; i++) {
                if (buttonClicked == btnArr[i]) {
                    buttonNumber = i;
                    break;
                }
            }
            GameStatus status = XO.playXO(buttonNumber);
            buttonClicked.setText(status.getPlayedChar());
            buttonClicked.setDisable(true);

            if (status.getWinnerName() != null) {
                String result = "You won!";
                ServerLayer.getMyPlayer().setScore(ServerLayer.getMyPlayer().getScore() + 10);
                drawWinnerLine(status.getWinCase());
                showResult(result);
                winnerName = ServerLayer.getMyPlayer().getName();
            } else if (status.isDraw()) {
                winnerName="noWinner";
                showResult("Draw");
            }

            if (XO.isTurn()) {
                ServerLayer.sendCurrentPlay(XO.getPlayer2().getName(), buttonNumber, winnerName);
                System.out.println("getWinnerName() " + status.getWinnerName());

            } else {
                ServerLayer.sendCurrentPlay(XO.getPlayer1().getName(), buttonNumber, winnerName);
                System.out.println("getWinnerName() " + status.getWinnerName());
            }
            disableAllBtns();

        } else {
            buttonNumber = ServerLayer.secondPlayerPosition;
            GameStatus status = XO.playXO(buttonNumber);
            btnArr[buttonNumber].setText(status.getPlayedChar());
            btnArr[buttonNumber].setDisable(true);
            //////////////////////////////////////////////
            if (status.getWinnerName() != null) {
                String result = "You lost!";
                ServerLayer.getMyPlayer().setScore(ServerLayer.getMyPlayer().getScore() - 10);
                drawWinnerLine(status.getWinCase());
                showResult(result);

            } else if (status.isDraw()) {
                showResult("Draw");
            }
            enableAllBtns();
        }
        if (XO.isTurn()) {
            XO.setTurn(false);
            player2Pane.setStyle(playerPaneColor);
            player1Pane.setStyle("");

        } else {
            XO.setTurn(true);
            player1Pane.setStyle(playerPaneColor);
            player2Pane.setStyle("");
        }
    }

    private void disableAllBtns() {
        for (int i = 0; i < 9; i++) {
            btnArr[i].setDisable(true);
        }
    }

    private void enableAllBtns() {
        for (int i = 0; i < 9; i++) {
            if (btnArr[i].getText().isEmpty()) {
                btnArr[i].setDisable(false);
            }
        }
    }

    public void callButtonHandller() {
        handleButtonClick(null);
    }

    @FXML
    private void returnHomeAction(MouseEvent event) {
        try {
            if (GamePlay.mode == "Online") {

                SceneController.navigateToOnlinePlayers(event);

            } else {
                SceneController.navigateToHome(event);
            }
        } catch (IOException ex) {
            System.out.println("error navigating to home after gameplay");
        }
    }
}
