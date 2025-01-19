/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import classes.ServerLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author uesr
 */
public class LoginController implements Initializable {

    private Label label;
    @FXML
    private TextField user_name_tf_login;
    @FXML
    private PasswordField password_tf_login;
    @FXML
    private Label user_name_label;
    @FXML
    private Label password_label;
    @FXML
    private Button button_login;
    @FXML
    private Text creat_account_t_login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //user name label 
        user_name_label.setTextFill(Color.color(1, 0, 0));
        user_name_label.setText("Please set your user name");
        user_name_label.setVisible(false);

        // password label 
        password_label.setVisible(false);
        password_label.setTextFill(Color.color(1, 0, 0));
        password_label.setText("Please set your Password");

    }

    @FXML
    private void createAccountAction(MouseEvent event) {
        try {
            SceneController.navigateToSignup(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) {
        String userName = "";
        String password = "";
        //login button logic 
        if (user_name_tf_login.getText() == null || user_name_tf_login.getText().trim().isEmpty()) {
            user_name_label.setVisible(true);
        }
        if (password_tf_login.getText() == null || password_tf_login.getText().trim().isEmpty()) {
            password_label.setVisible(true);
        }

        if (user_name_tf_login.getText() != null && !user_name_tf_login.getText().trim().isEmpty()) {
            user_name_label.setVisible(false);
            userName = user_name_tf_login.getText();
        }
        if (password_tf_login.getText() != null && !password_tf_login.getText().trim().isEmpty()) {
            password_label.setVisible(false);
            password = password_tf_login.getText();
        }
        if (user_name_tf_login.getText() != null && !user_name_tf_login.getText().trim().isEmpty() && password_tf_login.getText() != null && !password_tf_login.getText().trim().isEmpty()) {
            ServerLayer.loginRequest(userName, password);
            System.out.println("request sended ");
            if (ServerLayer.flagCheckResponse) {
                checkLoginResponse(event);
            }
            ServerLayer.flagCheckResponse = false;
        }
    }

    private void checkLoginResponse(ActionEvent event) {
        if (ServerLayer.loginResponse()) {
            System.out.println("user found (in controller)");
            SceneController sceneController = new SceneController();
            try {
                sceneController.navigateToOnlinePlayers(event);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            user_name_label.setText("user not found");
            user_name_label.setVisible(true);
        }

    }
}
