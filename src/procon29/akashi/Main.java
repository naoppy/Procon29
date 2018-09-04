package procon29.akashi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import procon29.akashi.gui.Viewer;

public class Main extends Application {

    private GameBoard gameBoard = new GameBoard();

    private Viewer viewer = new Viewer(gameBoard);

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
/*
8 11:-2 1 0 1 2 0 2 1 0 1 -2:1 3 2 -2 0 1 0 -2 2 3 1:1 3 2 1 0 -2 0 1 2 3 1:2 1 1
2 2 3 2 2 1 1 2:2 1 1 2 2 3 2 2 1 1 2:1 3 2 1 0 -2 0 1 2 3 1:1 3 2 -2 0 1 0 -2 2 3
1:-2 1 0 1 2 0 2 1 0 1 -2:2 2:7 10:a
 */
