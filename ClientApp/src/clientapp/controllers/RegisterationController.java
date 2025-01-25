/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.Player;
import classes.ServerLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * FXML Controller class
 *
 * @author uesr
 */
public class RegisterationController implements Initializable {

    @FXML
    private TextField user_name_tf_registration;
    @FXML
    private TextField email_tf_registration;
    @FXML
    private PasswordField password_tf_registration;
    @FXML
    private PasswordField confirm_password_tf_registration;
    @FXML
    private Button button_registration;
    @FXML
    private Text login_text;
    @FXML
    private Label user_name_label_registration;
    @FXML
    private Label email_label_registration;
    @FXML
    private Label password_label_registration;
    @FXML
    private Label confirm_password_label_registration;
    @FXML
    private ImageView homeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        if (ServerLayer.getOutputStream() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Register");
            alert.setHeaderText(null);
            alert.setContentText("Server Connection Error");
            try {
                if (ServerLayer.reconnectToServer(user_name_tf_registration.getText().trim(), email_tf_registration.getText().trim(), password_tf_registration.getText().trim())) {
                    ServerLayer.reRegisterClient(user_name_tf_registration.getText().trim(), email_tf_registration.getText().trim(), password_tf_registration.getText().trim());
                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Register");
                    alert.setHeaderText(null);
                    alert.setContentText("Can't reconnect");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("An error occurred", "Could not complete registration.");
            }
            return;
        }
        String username = user_name_tf_registration.getText().trim();
        String password = password_tf_registration.getText().trim();
        String confirmPassword = confirm_password_tf_registration.getText().trim();
        String email = email_tf_registration.getText().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            user_name_label_registration.setText("Username cannot be empty.");
            user_name_label_registration.setVisible(true);
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.println("Invalid email format.");
            email_label_registration.setText("Invalid email format.");
            email_label_registration.setVisible(true);
            return;
        }

        if (password.isEmpty()) {
            System.out.println("Please write a Password.");
            password_label_registration.setText("Password fields cannot be empty.");
            password_label_registration.setVisible(true);
            return;
        }
        if (confirmPassword.isEmpty()) {
            System.out.println("Please write a Password.");
            confirm_password_label_registration.setText("Password confirm password.");
            confirm_password_label_registration.setVisible(true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            confirm_password_label_registration.setText("Passwords do not match.");
            confirm_password_label_registration.setVisible(true);
            return;
        }
        ServerLayer.registerRequest(username,password,email);
        
        
    }

    public void updateRegistrationStatusLabel(String statusMessage,boolean successState) {
        if (user_name_label_registration != null) {
            user_name_label_registration.setText(statusMessage);
            user_name_label_registration.setVisible(true);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Register");
            alert.setHeaderText(null);
            alert.setContentText(statusMessage);

            alert.showAndWait();
            System.out.println("Updated label: " + statusMessage);
            try {
                if(successState)
                {
                    SceneController.navigateToOnlinePlayers(null);
                }
                
            } catch (IOException ex) {
                System.out.println("error navigating to online players in register controller");
            }
        } else {
            System.err.println("registrationStatusLabel is null. Unable to update status.");

        }
    }

    @FXML
    private void loginAction(MouseEvent event) {
        try {
            SceneController.navigateToLogin(event);
        } catch (IOException ex) {
            Logger.getLogger(RegisterationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void sendRegistrationRequest() {
//        JsonObjectBuilder value = Json.createObjectBuilder();
//        JsonObject registrationMessage = value
//                .add("Header", "register")
//                .add("username", "YourUsername") // Replace with actual username
//                .build();
//        ServerLayer.getOutputStream().println(registrationMessage.toString());
//        System.out.println("Registration message sent.");
//    }
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onHoverExitLogin(MouseEvent event) {
    }

    @FXML
    private void onHoverLogin(MouseEvent event) {
    }

    @FXML
    private void homeButton(MouseEvent event) {
        try {
            SceneController.navigateToHome(event);
        } catch (IOException ex) {
            Logger.getLogger(RegisterationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
