package classes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public  class Registration extends AnchorPane {

    protected final ImageView imageView;
    protected final Text text;
    protected final TextField user_name_tf_registration;
    protected final TextField email_tf_registration;
    protected final PasswordField password_tf_registration;
    protected final PasswordField confirm_password_tf_registration;
    protected final Button button_registration;
    protected final Text text0;
    protected final Text login_text;
    protected final Label user_name_label_registration;
    protected final Label email_label_registration;
    protected final Label password_label_registration;
    protected final Label confirm_password_label_registration;

    public Registration() {

        imageView = new ImageView();
        text = new Text();
        user_name_tf_registration = new TextField();
        email_tf_registration = new TextField();
        password_tf_registration = new PasswordField();
        confirm_password_tf_registration = new PasswordField();
        button_registration = new Button();
        text0 = new Text();
        login_text = new Text();
        user_name_label_registration = new Label();
        email_label_registration = new Label();
        password_label_registration = new Label();
        confirm_password_label_registration = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(770.0);
        setPrefWidth(950.0);
        setStyle("-fx-background-color: #8CCDF7;");

        imageView.setFitHeight(77.0);
        imageView.setFitWidth(302.0);
        imageView.setLayoutX(390.0);
        imageView.setLayoutY(14.0);
        imageView.setNodeOrientation(javafx.geometry.NodeOrientation.INHERIT);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("Screenshot_2025-01-07_172841-removebg-preview.png").toExternalForm()));

        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(400.0);
        text.setLayoutY(172.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Registration");
        text.setFont(new Font(33.0));

        
        user_name_tf_registration.setLayoutX(400.0);
        user_name_tf_registration.setLayoutY(221.0);
        user_name_tf_registration.setPromptText("User name");

        email_tf_registration.setLayoutX(400.0);
        email_tf_registration.setLayoutY(289.0);
        email_tf_registration.setPromptText("Email@gmail.com");

        password_tf_registration.setLayoutX(400.0);
        password_tf_registration.setLayoutY(352.0);
        password_tf_registration.setPromptText("Password");

        confirm_password_tf_registration.setLayoutX(400.0);
        confirm_password_tf_registration.setLayoutY(409.0);
        confirm_password_tf_registration.setPromptText("Confirm password");

        button_registration.setAlignment(javafx.geometry.Pos.CENTER);
        button_registration.setLayoutX(400.0);
        button_registration.setLayoutY(477.0);
        button_registration.setMnemonicParsing(false);
        button_registration.setPrefWidth(150.0);
        button_registration.setStyle("-fx-background-color: #ffffff;");
        button_registration.setText("Register");
        
        //registration button logic 
        button_registration.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               
            }
        
        });

        text0.setFill(javafx.scene.paint.Color.valueOf("#f5f0f0"));
        text0.setLayoutX(400.0);
        text0.setLayoutY(544.0);
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(0.0);
        text0.setText("Already have an account ?");

        login_text.setLayoutX(549.0);
        login_text.setLayoutY(544.0);
        login_text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        login_text.setStrokeWidth(0.0);
        login_text.setText("login");

        user_name_label_registration.setLayoutX(400.0);
        user_name_label_registration.setLayoutY(245.0);
        // set label to red when there is warning
        user_name_label_registration.setTextFill(Color.color(1, 0, 0));
        user_name_label_registration.setText("please set your user name");
        user_name_label_registration.setVisible(false);
         

        email_label_registration.setLayoutX(400.0);
        email_label_registration.setLayoutY(314.0);

        password_label_registration.setLayoutX(400.0);
        password_label_registration.setLayoutY(377.0);

        confirm_password_label_registration.setLayoutX(400.0);
        confirm_password_label_registration.setLayoutY(434.0);

        getChildren().add(imageView);
        getChildren().add(text);
        getChildren().add(user_name_tf_registration);
        getChildren().add(email_tf_registration);
        getChildren().add(password_tf_registration);
        getChildren().add(confirm_password_tf_registration);
        getChildren().add(button_registration);
        getChildren().add(text0);
        getChildren().add(login_text);
        getChildren().add(user_name_label_registration);
        getChildren().add(email_label_registration);
        getChildren().add(password_label_registration);
        getChildren().add(confirm_password_label_registration);

    }
}
