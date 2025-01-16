/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

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
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void createAccountAction(MouseEvent event) {
        try {
            new SceneController().navigateToSignup(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
