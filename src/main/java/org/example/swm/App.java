package org.example.swm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The class App serving as entry point.
 */
public class App extends Application {
    /**
     * Start method for creating java fx scene. Entry point of application
     * @param stage stage of type Stage
     * @throws Exception throws any exception found
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();

    }
}
