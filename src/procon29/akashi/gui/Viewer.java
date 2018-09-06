package procon29.akashi.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import procon29.akashi.GameBoard;

import java.awt.*;
import java.io.IOException;

/**
 * GameBoardを視覚化するクラス
 */
public class Viewer {
    /**
     * GUIの基底
     */
    private BorderPane root;
    /**
     * GUIのコントローラークラス
     */
    private Controller controller;
    /**
     * Viewerが視覚化する対象
     */
    private GameBoard gameBoard;

    /**
     * 渡されたGameBoardの状態に基づいてViewerを作る
     *
     * @param gameBoard 視覚化する対象
     */
    public Viewer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        try {
            //load FXML and load controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/root2.fxml"));
            root = loader.load();
            controller = loader.getController();

            for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
                for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                    //ImageViewをクリックすると追加できるように
                    ImageView imageView = new ImageView("NoneTile.png");
                    int yy = y, xx = x;
                    imageView.setOnMouseClicked(event -> {
                        Point p = new Point(xx, yy);
                        if (!gameBoard.enemyPlayerSet.remove(p) && gameBoard.enemyPlayerSet.size() < 2) {
                            gameBoard.enemyPlayerSet.add(p);
                        }
                        this.firstViewUpdate();
                    });
                    controller.grid.add(imageView, x, y);

                    //ボタンを押したら決定できるように
                    controller.solveBotton.setOnMouseClicked(event -> {
                        if (gameBoard.decideEnemyPlayerPlace()) startNextPhase();
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.firstViewUpdate();
    }

    private void startNextPhase() {
        clearEventHandler();
        gameBoard.solve();
        controller.solveBotton.setOnMouseClicked(event1 -> gameBoard.nextStage());
    }

    /**
     * 敵プレイヤーの暫定位置を表示する
     */
    private void firstViewUpdate() {
        int w = gameBoard.maker.getWidth();

        for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
            for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                ImageView imageView = (ImageView) controller.grid.getChildren().get(w * y + x);
                imageView.setImage(new Image("NoneTile.png"));
            }
        }
        ImageView imageView = (ImageView) controller.grid.getChildren().get(w * gameBoard.maker.getFp1().y + gameBoard.maker.getFp1().x);
        imageView.setImage(new Image("FriendPlayer1.png"));
        imageView = (ImageView) controller.grid.getChildren().get(w * gameBoard.maker.getFp2().y + gameBoard.maker.getFp2().x);
        imageView.setImage(new Image("FriendPlayer2.png"));
        Point[] enemys = gameBoard.enemyPlayerSet.toArray(new Point[2]);
        switch (gameBoard.enemyPlayerSet.size()) {
            case 2:
                imageView = (ImageView) controller.grid.getChildren().get(w * enemys[1].y + enemys[1].x);
                imageView.setImage(new Image("EnemyPlayer2.png"));
            case 1:
                imageView = (ImageView) controller.grid.getChildren().get(w * enemys[0].y + enemys[0].x);
                imageView.setImage(new Image("EnemyPlayer1.png"));
                break;
        }


    }

    /**
     * 表示の為に現在のゲーム画面のGUIを返す
     *
     * @return GUIの基底
     */
    public Parent getView() {
        return root;
    }

    /**
     * クリックイベントを全て削除する
     */
    private void clearEventHandler() {
        int w = gameBoard.maker.getWidth();

        for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
            for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                ImageView imageView = (ImageView) controller.grid.getChildren().get(w * y + x);
                imageView.setOnMouseClicked(null);
            }
        }
    }
}
