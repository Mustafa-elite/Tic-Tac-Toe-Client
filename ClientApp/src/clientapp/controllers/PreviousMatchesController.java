/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.GamePlay;
import classes.MatchRecorder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author user
 */
public class PreviousMatchesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    static ObservableList<File> fileList;

    @FXML
    private ImageView homePage;
    @FXML
    private ListView<File> fileViewList;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileList=FXCollections.observableArrayList();
        fileViewList.setItems(fileList);
        fileViewList.setCellFactory(listview->new MatchesCellView());
        getFileList();
        
        
        
        
        // TODO
    }    

    @FXML
    private void HomeBtn(MouseEvent event) {
        try {
            SceneController.navigateToOnlinePlayers(event);
        } catch (IOException ex) {
            Logger.getLogger(PreviousMatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getFileList(){
    File directory = new File("../../Recordings/");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                        fileList.add(file);
                }
            }
        } else {
            System.out.println("Invalid directory path!");
        }

    }
    public static void viewMatch(int matchIndex)
    {
        System.out.println(fileList.get(matchIndex).getAbsolutePath());
        MatchRecorder.readRecordFile(fileList.get(matchIndex));
        
        GamePlay.mode = "Local";
        GamePlay.record=false;
        try {
            SceneController.navigateToXOBoard(null);
        } catch (IOException ex) {
            Logger.getLogger(PreviousMatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


class MatchesCellView extends ListCell<File>{
    private final  HBox hbox = new HBox();
    private final  Button replayButton = new Button("Replay");
    private final Label fileNameLabel = new Label();
    private final Region space = new Region();
     public MatchesCellView() {
        super();
        HBox.setHgrow(space, Priority.ALWAYS);
        replayButton.setOnAction(event -> {
            
            PreviousMatchesController.viewMatch(getIndex());
        });
        replayButton.setStyle(
                "-fx-background-color: #ffffff; " +
                "-fx-text-fill: #629cba; " +
                "-fx-background-radius: 20; " +
                "-fx-padding: 5 15; " +
                "-fx-font-size: 16;"
        );
        fileNameLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #ffffff; -fx-padding: 5 0;");
        hbox.setSpacing(10);
        hbox.setStyle("-fx-padding: 10; -fx-background-color: #629CBA; -fx-background-radius: 5;");
        hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        HBox.setHgrow(space, Priority.ALWAYS);
        hbox.getChildren().addAll(fileNameLabel,space,replayButton);
    }
    
    protected void updateItem(File f, boolean empty) {
        super.updateItem(f, empty); 

        if (empty || f== null) {
            setText(null);
            setGraphic(null);
        }
        else
        {
            //setText(f.getName().substring(0, f.getName().length()-4));
            fileNameLabel.setText(f.getName().substring(0, f.getName().length()-4));
            
            
            //setGraphicTextGap(500);
            //setTextAlignment(TextAlignment.LEFT);
            setGraphic(hbox);
            
            
        }
    }
    
}

