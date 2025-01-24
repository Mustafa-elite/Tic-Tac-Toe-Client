/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import clientapp.controllers.GameRequestController;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 *
 * @author user
 */
public class GameRequestManager {

    private static GameRequestManager instance;
    private Pane gameRequestPane;
    private GameRequestController controller;

    private GameRequestManager() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/views/GameRequestPane.fxml"));
            gameRequestPane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameRequestManager getInstance() {
        if (instance == null) {
            instance = new GameRequestManager();
        }
        return instance;
    }

    public void displayRequest(String playerName, Scene scene) {
        Pane root = (Pane) scene.getRoot();

        if (!root.getChildren().contains(gameRequestPane)) {
            root.getChildren().add(gameRequestPane);
        }

        // Position the pane
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        double paneWidth = gameRequestPane.getPrefWidth();
        double paneHeight = gameRequestPane.getPrefHeight();
        gameRequestPane.setLayoutX(sceneWidth - paneWidth - 20);
        gameRequestPane.setLayoutY((sceneHeight - paneHeight) / 2);

        gameRequestPane.setVisible(true);
        gameRequestPane.toFront();
        controller.displayGameRequest(playerName);

        root.applyCss(); // Reapply styles for the root node
        root.layout(); // Ensure layout updates

        
    }
}
