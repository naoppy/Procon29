package procon29.akashi.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import procon29.akashi.GameBoard;
import procon29.akashi.Selection;
import procon29.akashi.owners.Owner;
import procon29.akashi.owners.OwnerToImageConverter;
import procon29.akashi.players.Player;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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

            stringsUpdate();

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

            inputSide();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.firstViewUpdate();
    }

    /**
     * 自分のチームが右か左か入力する
     */
    private void inputSide() {
        Scanner sc = new Scanner(System.in);
        int i;
        do {
            System.out.println("舞台側を上として、チームの位置は右か左か入力してください。");
            System.out.println("1：右　　2：左");
            i = sc.nextInt();
        } while (!(i == 1 || i == 2));

        controller.rightSide.setText(i == 1 ? "味方" : "相手");
        controller.leftSide.setText(i == 1 ? "相手" : "味方");
    }

    /**
     * コントローラーのLabelを更新する
     */
    private void stringsUpdate() {
        controller.cnt1.setText("後" + gameBoard.getRemainTurnNumber() + "ターン");
        controller.cnt2.setText("今" + (gameBoard.maker.getTurnNumber() - gameBoard.getRemainTurnNumber()) + "ターン目");
    }

    /**
     * ゲームのメインループに入る
     */
    private void startNextPhase() {
        clearAndSetEventHandler();
        gameBoard.solve();
        reView();
        controller.solveBotton.setOnMouseClicked(event1 -> {
            if (gameBoard.nextStage()) {
                stringsUpdate();
                gameBoard.solve();
            }
            clearAndSetEventHandler();
            reView();
        });
    }


    /**
     * GameBoardの所有者マップを基にimageViewの画像を変更する
     */
    private void reView() {
        int w = gameBoard.maker.getWidth();

        for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
            for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                ImageView imageView = (ImageView) controller.grid.getChildren().get(w * y + x);
                Owner nowOwner = gameBoard.getOwn(x, y);
                imageView.setImage(OwnerToImageConverter.convert(nowOwner));
            }
        }

        Image[] images = {new Image("FriendPlayer1.png"), new Image("FriendPlayer2.png"), new Image("EnemyPlayer1.png"), new Image("EnemyPlayer2.png")};
        AtomicInteger i = new AtomicInteger();

        Arrays.stream(gameBoard.players).forEach(player -> {
            ImageView imageView = (ImageView) controller.grid.getChildren().get(w * player.getNowPoint().y + player.getNowPoint().x);
            imageView.setImage(images[i.getAndAdd(1)]);
        });
    }

    /**
     * クリックイベントを全て削除する
     */
    private void clearAndSetEventHandler() {
        int w = gameBoard.maker.getWidth();

        //全てのノードのクリックイベントを削除
        for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
            for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                ImageView imageView = (ImageView) controller.grid.getChildren().get(w * y + x);
                imageView.setOnMouseClicked(null);
            }
        }
        //敵プレイヤーをクリックして行動を選べるように設定する
        Arrays.stream(gameBoard.players).skip(2L).forEach(this::setHandlerToSelect);
    }

    /**
     * 敵の動きを入力させるためにイベントハンドラを設定する
     */
    private void setHandlerToSelect(Player targetPlayer) {
        int w = gameBoard.maker.getWidth();

        controller.grid.getChildren().get(w * targetPlayer.getNowPoint().y + targetPlayer.getNowPoint().x).setOnMouseClicked(event1 -> {
            int[] diffX = {0, 1, 1, 0, -1, -1, -1, 0, 1}, diffY = {0, 0, 1, 1, 1, 0, -1, -1, -1};

            for (int dy : diffY) {
                for (int dx : diffX) {
                    int targetY = targetPlayer.getNowPoint().y + dy, targetX = targetPlayer.getNowPoint().x + dx;
                    //範囲内なら
                    if (targetY >= 0 && targetY < gameBoard.maker.getHeight() && targetX >= 0 && targetX < gameBoard.maker.getWidth()) {
                        controller.grid.getChildren().get(w * targetY + targetX).setOnMouseClicked(event2 -> {
                            targetPlayer.select(gameBoard.getOwn(targetX, targetY) == Owner.Friend ? Selection.REMOVE : Selection.MOVE, new Point(targetX, targetY));
                            clearAndSetEventHandler();
                        });
                    }
                }
            }
        });
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

}
