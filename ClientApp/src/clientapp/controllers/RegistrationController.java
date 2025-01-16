/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author uesr
 */
public class RegistrationController implements Initializable {

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
        System.out.println("registration clicked");
        if (user_name_tf_registration == null || user_name_tf_registration.getText().trim().isEmpty()) {
            user_name_label_registration.setVisible(true);
        } else {
            user_name_label_registration.setVisible(false);
        }
    }

}
