package org.example.swm;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.swm.models.IdGenerator;
import org.example.swm.models.ViewFactoryModel;

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
    public void start(Stage stage) {
        ViewFactoryModel.getInstance().getViewFactory().showLoginWindow();
    }

    /**
     * Stop method for saving id generator state
     */
    @Override
    public void stop() {
        IdGenerator.saveState();
    }


}
