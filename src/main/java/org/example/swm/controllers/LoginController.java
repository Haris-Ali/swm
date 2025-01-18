package org.example.swm.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.swm.models.ViewFactoryModel;
import org.example.swm.services.PasswordService;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * The type Login controller.
 */
public class LoginController implements Initializable {
    /**
     * The Username field.
     */
    public TextField username_field;
    /**
     * The Password field.
     */
    public PasswordField password_field;
    /**
     * The Login button.
     */
    public Button login_button;
    /**
     * The Error label.
     */
    public Label error_label;
    /**
     * The Success label.
     */
    public Label success_label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_button.setOnAction(e -> login());
    }

    /**
     * Function to handle the login functionality
     */
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
                Stage stage = (Stage) login_button.getScene().getWindow();
                ViewFactoryModel.getInstance().getViewFactory().closeStage(stage);
                ViewFactoryModel.getInstance().getViewFactory().showDashboardWindow();
            } else {
                error_label.setText("Invalid username or password. Please try again.");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            error_label.setText("An error occurred during login: " + e.getMessage());
        }
    }
}

