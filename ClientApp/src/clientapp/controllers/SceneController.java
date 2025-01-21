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
public  class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    public static void navigateToHome(Event event) throws IOException
    {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/HomePage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void navigateToOnlinePlayers(Event event) throws IOException
    {

        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/onlineClientsList.fxml"));
        if(event!=null)
        {    
            stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void navigateToXOBoard(Event event) throws IOException
    {

        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Board.fxml"));
        if(event!=null)
        {    
            stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void navigateToLogin(Event event) throws IOException
    {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Login.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void navigateToSignup(Event event) throws IOException
    {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Registeration.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void navigateToGameRecords(Event event) throws IOException
    {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/PreviousMatches.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void navigateGameInitializer(Event event) throws IOException
    {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/GameInitializer.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    
}
