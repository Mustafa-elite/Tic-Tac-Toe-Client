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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
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

        JsonObjectBuilder value = Json.createObjectBuilder();
        JsonObject jsonmsg = value
                .add("Header", "register")
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .build();

        ServerLayer.getOutputStream().println(jsonmsg.toString());
        System.out.println("Registration message sent: " + jsonmsg);
    }

    public void updateRegistrationStatusLabel(String statusMessage) {
        if (user_name_label_registration != null) {
            user_name_label_registration.setText(statusMessage);
            user_name_label_registration.setVisible(true);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Register");
            alert.setHeaderText(null);
            alert.setContentText(statusMessage);

            alert.showAndWait();
            System.out.println("Updated label: " + statusMessage);;
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

}
