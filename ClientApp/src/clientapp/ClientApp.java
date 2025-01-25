
package clientapp;

import classes.GamePlay;
import classes.ServerLayer;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


public class ClientApp extends Application {

     private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        ClientApp.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/HomePage.fxml"));

        Scene scene = new Scene(root);

        primaryStage=stage;
        


        stage.setScene(scene);
        
        stage.setOnCloseRequest(event -> closeApp(event));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void closeApp(Event event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("You will Lose the Game if You are inside a game");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(GamePlay.mode.equals("Online"))
                {
                    if(ServerLayer.getMyPlayer()!=null)
                    {
                        ServerLayer.sendLogoutRequest();
                        Platform.exit();
                        System.exit(0);
                    }
                }
                
            } else {
                // Prevent the application from closing
                event.consume();
            }
        
    }

}
