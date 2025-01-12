/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import classes.GamePlay;
import classes.GameStatus;
import classes.LocalGamePlay;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 *
 * @author khali
 */
public class BoardController implements Initializable {
    
     GamePlay XO ;
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
    
   
    
    @FXML
    private void handleButtonClick(ActionEvent event) {
        
        int buttonNo=0;
        
        Button clickedButton = (Button) event.getSource();
        
        if(clickedButton==Button1) buttonNo=0;
        else if(clickedButton==Button2) buttonNo=1;
        else if(clickedButton==Button3) buttonNo=2;
        else if(clickedButton==Button4) buttonNo=3;
        else if(clickedButton==Button5) buttonNo=4;
        else if(clickedButton==Button6) buttonNo=5;
        else if(clickedButton==Button7) buttonNo=6;
        else if(clickedButton==Button8) buttonNo=7;
        else if(clickedButton==Button9) buttonNo=8;
        
        GameStatus status= XO.playXO(buttonNo);
        clickedButton.setText(status.getPlayedChar());
         clickedButton.setDisable(true); 
        if(status.getWinnerName()!=null) {
            ResultLabel.setText(status.getWinnerName());
            switch (status.getWinCase()){
                case GamePlay.WIN_ROW_1 :{
                    Row1.setVisible(true);
                    break;
                }
                case GamePlay.WIN_ROW_2 : {
                    Row2.setVisible(true);
                    break;
                }
                case GamePlay.WIN_ROW_3 : {
                    Row3.setVisible(true);
                    break;
                }
                case GamePlay.WIN_COL_1 : {
                    Col1.setVisible(true);
                    break;
                }
                case GamePlay.WIN_COL_2 : {
                    Col2.setVisible(true);
                    break;
                }
                case GamePlay.WIN_COL_3 : {
                    Col3.setVisible(true);
                    break;
                }
                case GamePlay.WIN_DIAGONAL_1 : {
                    Diagonal1.setVisible(true);
                    break;
                }
                case GamePlay.WIN_DIAGONAL_2 : {
                    Diagonal2.setVisible(true);
                    break;
                }

            }
            
            ResultPane.setVisible(true);    
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Create Object Based on Static Variable in GamePlay
        XO =new LocalGamePlay("Khalid","Mustafa") ;
    }    

    

  
    
}