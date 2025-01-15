package org.example.swm.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    public ViewFactory() {}

    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        createStage(fxmlLoader);
    }

    public void showDashboardWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
        createStage(fxmlLoader);
    }

    public void createStage(FXMLLoader fxmlLoader) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Staff Workload Management");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
