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
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 *
 * @author khali
 */
public class BoardController implements Initializable {

    GamePlay XO;
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
    private void handleButtonClick(ActionEvent event) {
        int buttonNo = -1;
        
        if (GamePlay.mode == "AI") {
           
            Button clickedButton=null;
            if(XO.isTurn())
            {
                clickedButton = (Button) event.getSource();
                for(int i=0;i<9;i++)
                {
                    if(clickedButton==btnArr[i])
                    {
                        buttonNo = i;
                        break;
                    }
                }
            }
            GameStatus status = XO.playXO(buttonNo);

            if (XO.isTurn()) {
                clickedButton.setText(status.getPlayedChar());
                clickedButton.setDisable(true);
            }
            else
            {
                btnArr[status.getPosition()].setText("X");
                btnArr[status.getPosition()].setDisable(true);
            }
            
            if (status.getWinnerName() != null) {
                ResultLabel.setText(status.getWinnerName());
                drawWinnerLine(status.getWinCase());
                ResultPane.setVisible(true);
                //////////their sould be navigation here
                return;
            }
            else if(status.isDraw())
            {
                ResultLabel.setText("Draw");
                ResultPane.setVisible(true);
            }
            if(XO.isTurn())
            {
                XO.setTurn(false);
                ////there should be delay here
                 PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(e -> handleButtonClick(null));
                pause.play();
                
            }
            else
            {
                XO.setTurn(true);
            }
            
        } else if (GamePlay.mode == "Local") {
            

            Button clickedButton = (Button) event.getSource();

            for(int i=0;i<9;i++)
            {
                if(clickedButton==btnArr[i])
                {
                    buttonNo = i;
                    break;
                }
            }
            GameStatus status = XO.playXO(buttonNo);
            clickedButton.setText(status.getPlayedChar());
            clickedButton.setDisable(true);
            if (status.getWinnerName() != null) {
                ResultLabel.setText(status.getWinnerName());
                drawWinnerLine(status.getWinCase());
                ResultPane.setVisible(true);
            }
            else if(status.isDraw())
            {
                ResultLabel.setText("Draw");
                ResultPane.setVisible(true);
            }
            if(XO.isTurn())
            {
                XO.setTurn(false);
            }
            else
            {
                XO.setTurn(true);
            }
            
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Create Object Based on Static Variable in GamePlay
        btnArr = new Button[]{Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9};
        if (GamePlay.mode == "AI") {
            XO = new AIGamePlay("Rana", "AI");
            if(!XO.isTurn())
            {
                handleButtonClick(null);
            }
        } else if (GamePlay.mode == "Local") {
            XO = new LocalGamePlay("Player1", "Player2");
        }
        else if(GamePlay.mode == "Online")
        {
            //parameters of online gameplay should be sent from acccept button(client 2) in OnlineClientListContoller and from receivegameacceptance(client 1)
            //XO= new OnlineGamePlay();
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

}
