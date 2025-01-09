/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void navigateToHome(Event event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/clientapp/views/HomePage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void navigateToOnlinePlayers(Event event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/clientapp/views/onlineClientsList.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void navigateToXOBoard(Event event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/clientapp/views/Board.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        /*public void navigateToXOBoard(Event event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/clientapp/views/Board.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/
    
    
}
