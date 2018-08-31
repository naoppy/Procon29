package procon29.akashi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import procon29.akashi.players.BlueTeamPlayer;
import procon29.akashi.players.RedTeamPlayer;
import procon29.akashi.scores.QRInputer;
import procon29.akashi.scores.ScoreFromQR;
import procon29.akashi.scores.ScoreMaker;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * ゲームの進行を管理する最上位クラス
 */
public class GameBoard {
    /**
     * スコアと自分の位置を与える
     */
    private ScoreMaker maker = new ScoreFromQR(QRInputer.inputCode());
    /**
     * スコア
     */
    private int[][] scores = maker.getMap();
    /**
     * どのチームの領域か
     */
    private Owner[][] owners = new Owner[maker.getHeight()][maker.getWidth()];
    /**
     * 自分のチームのPlayer2人
     */
    private RedTeamPlayer rp1 = new RedTeamPlayer(maker.getRp1()), rp2 = new RedTeamPlayer(maker.getRp2());
    /**
     * 相手のチームのPlayer2人
     */
    private BlueTeamPlayer bp1, bp2;
    /**
     * GUIの基底
     */
    private BorderPane root;
    /**
     * GUIのコントローラークラス
     */
    private Controller controller;
    /**
     * 青プレイヤーの初期位置を入力するためのセット
     */
    private Set<Point> bluePlayerSet = new HashSet<>();

    /**
     * GUIをFXMLから読み込んで生成
     */
    public GameBoard() {
        try {
            //load FXML and load controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/root2.fxml"));
            root = loader.load();
            controller = loader.getController();

            for (int y = 0; y < maker.getHeight(); y++) {
                for (int x = 0; x < maker.getWidth(); x++) {
                    //ImageViewをクリックすると追加できるように
                    ImageView imageView = new ImageView("NoneTile.png");
                    int yy = y, xx = x;
                    imageView.setOnMouseClicked(event -> {
                        Point p = new Point(xx, yy);
                        if (!bluePlayerSet.remove(p) && bluePlayerSet.size() < 2) {
                            bluePlayerSet.add(p);
                        }
                        this.firstViewUpdate();
                    });
                    controller.grid.add(imageView, x, y);

                    //ボタンを押したら決定できるように
                    controller.solveBotton.setOnMouseClicked(event -> this.decideBlueTeamPlace());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.firstViewUpdate();
    }

    /**
     * 敵エージェントの位置を決定する
     */
    private void decideBlueTeamPlace() {
        if (bluePlayerSet.size() != 2) return;

        Point[] points = bluePlayerSet.toArray(new Point[2]);
        bp1 = new BlueTeamPlayer(points[0]);
        bp2 = new BlueTeamPlayer(points[1]);

        //Solver.solve(scores, owners, rp1, rp2, bp1, bp2);


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
     * 敵プレイヤーの位置より初期のタイル所有マップを作る
     */
    private void firstViewUpdate() {
        int w = maker.getWidth();

        for (int y = 0; y < maker.getHeight(); y++) {
            for (int x = 0; x < maker.getWidth(); x++) {
                ImageView imageView = (ImageView) controller.grid.getChildren().get(w * y + x);
                imageView.setImage(new Image("NoneTile.png"));
            }
        }
        ImageView imageView = (ImageView) controller.grid.getChildren().get(w * rp1.getNowPoint().y + rp1.getNowPoint().x);
        imageView.setImage(new Image("FriendPlayer1.png"));
        imageView = (ImageView) controller.grid.getChildren().get(w * rp2.getNowPoint().y + rp2.getNowPoint().x);
        imageView.setImage(new Image("FriendPlayer2.png"));
        Point[] blues = bluePlayerSet.toArray(new Point[2]);
        switch (bluePlayerSet.size()) {
            case 2:
                imageView = (ImageView) controller.grid.getChildren().get(w * blues[1].y + blues[1].x);
                imageView.setImage(new Image("EnemyPlayer2.png"));
            case 1:
                imageView = (ImageView) controller.grid.getChildren().get(w * blues[0].y + blues[0].x);
                imageView.setImage(new Image("EnemyPlayer1.png"));
                break;
        }
    }
}
