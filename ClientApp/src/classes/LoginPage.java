package classes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginPage extends BorderPane {

    protected final ImageView imageView;
    protected final AnchorPane anchorPane;
    protected final TextField user_name_tf_login;
    protected final PasswordField password_tf_login;
    protected final Label user_name_label;
    protected final Label password_label;
    protected final Button button_login;
    protected final HBox hBox;
    protected final Text text;
    protected final Text creat_account_t_login;
    protected final Text text0;

    public LoginPage() {

        imageView = new ImageView();
        anchorPane = new AnchorPane();
        user_name_tf_login = new TextField();
        password_tf_login = new PasswordField();
        user_name_label = new Label();
        password_label = new Label();
        button_login = new Button();
        hBox = new HBox();
        text = new Text();
        creat_account_t_login = new Text();
        text0 = new Text();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #8CCDF7;");

        BorderPane.setAlignment(imageView, javafx.geometry.Pos.CENTER);
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("Screenshot_2025-01-07_172841-removebg-preview.png").toExternalForm()));
        setTop(imageView);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);

        user_name_tf_login.setLayoutX(226.0);
        user_name_tf_login.setLayoutY(87.0);
        user_name_tf_login.setPromptText("User name");

        password_tf_login.setLayoutX(226.0);
        password_tf_login.setLayoutY(138.0);
        password_tf_login.setPromptText("Password");
        // user name label 
        user_name_label.setLayoutX(226.0);
        user_name_label.setLayoutY(112.0);
        user_name_label.setTextFill(Color.color(1, 0, 0));
        user_name_label.setText("Please set your user name");
        user_name_label.setVisible(false);
        // password label 
        password_label.setLayoutX(226.0);
        password_label.setLayoutY(163.0);
        password_label.setVisible(false);
        password_label.setTextFill(Color.color(1, 0, 0));
        password_label.setText("Please set your Password");

        button_login.setLayoutX(274.0);
        button_login.setLayoutY(204.0);
        button_login.setMnemonicParsing(false);
        button_login.setStyle("-fx-background-color: #ffffff;");
        button_login.setText("Login");
        button_login.setTextFill(javafx.scene.paint.Color.valueOf("#8ccdf7"));

        //login button logic 
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (user_name_tf_login.getText() == null || user_name_tf_login.getText().trim().isEmpty()) {
                    user_name_label.setVisible(true);
                }
                if (password_tf_login.getText() == null || password_tf_login.getText().trim().isEmpty()) {
                    password_label.setVisible(true);
                }
                //System.out.println("button clicked");
                if (user_name_tf_login.getText() != null && !user_name_tf_login.getText().trim().isEmpty()) {
                    user_name_label.setVisible(false);
                }
                if (password_tf_login.getText() != null && !password_tf_login.getText().trim().isEmpty()) {
                    password_label.setVisible(false);
                }
            }

        });

        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setLayoutX(200.0);
        hBox.setLayoutY(229.0);
        hBox.setPrefHeight(100.0);
        hBox.setPrefWidth(200.0);

        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Dont have an account ?");

        creat_account_t_login.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        creat_account_t_login.setStrokeWidth(0.0);
        creat_account_t_login.setText("Creat one");
        creat_account_t_login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    System.out.println("text clicked") ; 
            }

        });
        HBox.setMargin(creat_account_t_login, new Insets(0.0, 0.0, 0.0, 5.0));

        text0.setFill(javafx.scene.paint.Color.WHITE);
        text0.setLayoutX(240.0);
        text0.setLayoutY(53.0);
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(5.0);
        text0.setText("LOGIN");
        text0.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text0.setFont(new Font("System Bold", 36.0));
        setCenter(anchorPane);

        anchorPane.getChildren().add(user_name_tf_login);
        anchorPane.getChildren().add(password_tf_login);
        anchorPane.getChildren().add(user_name_label);
        anchorPane.getChildren().add(password_label);
        anchorPane.getChildren().add(button_login);
        hBox.getChildren().add(text);
        hBox.getChildren().add(creat_account_t_login);
        anchorPane.getChildren().add(hBox);
        anchorPane.getChildren().add(text0);

    }
}
