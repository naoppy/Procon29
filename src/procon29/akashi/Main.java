package procon29.akashi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Viewer
     */
    private Viewer viewer = new Viewer();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Procon29 Visualizer");
        primaryStage.setScene(new Scene(viewer.getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
