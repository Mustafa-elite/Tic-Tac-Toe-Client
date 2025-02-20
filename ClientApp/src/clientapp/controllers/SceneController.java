package clientapp.controllers;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class SceneController {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public static void navigateToHome(Event event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToOnlinePlayers(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/clientapp/views/onlineClientsList.fxml"));
        root = loader.load();
        OnlineClientsListController controller = loader.getController();
        controller.startRefreshing();
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToXOBoard(Event event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Board.fxml"));
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToLogin(Event event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToSignup(Event event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Registeration.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToGameRecords(Event event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/PreviousMatches.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateGameInitializer(Event event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/GameInitializer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToProfile(MouseEvent event) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/clientapp/views/Profile.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}