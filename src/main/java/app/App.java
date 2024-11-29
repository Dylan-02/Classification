package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        new UserInterface();
    }

    public static void main(String[] args) {
        launch();
    }
}