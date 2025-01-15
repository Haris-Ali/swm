package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.swm.services.PasswordService;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField username_field;
    public PasswordField password_field;
    public Button login_button;
    public Label error_label;
    public Label success_label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_button.setOnAction(e -> login());
    }

    public void login() {
        String username = username_field.getText();
        String password = password_field.getText();

        error_label.setText("");
        success_label.setText("");
        if (username.isEmpty() || password.isEmpty()) {
            error_label.setText("Username and password are required");
            return;
        }

        PasswordService ps = new PasswordService();
        try {
            if (ps.verifyUser(username_field.getText(), password_field.getText())) {
                success_label.setText("Login successful!");
            } else {
                error_label.setText("Invalid username or password. Please try again.");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            error_label.setText("An error occurred during login: " + e.getMessage());
        }
    }
}

