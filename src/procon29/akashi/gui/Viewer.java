package procon29.akashi.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import procon29.akashi.GameBoard;
import procon29.akashi.owners.Owner;
import procon29.akashi.owners.OwnerToImageConverter;
import procon29.akashi.players.Player;
import procon29.akashi.scores.ScoreToImageConverter;
import procon29.akashi.selection.*;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
     * プレイヤーの画像をキャッシュしておく
     */
    private static Image[] images = {new Image("FriendPlayer1.png"), new Image("FriendPlayer2.png"), new Image("EnemyPlayer1.png"), new Image("EnemyPlayer2.png")};

    /**
     * 渡されたGameBoardの状態に基づいてViewerを作る
     *
     * @param gameBoard 視覚化する対象
     */
    public Viewer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        try {
            //load FXML and load controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/root.fxml"));
            root = loader.load();
            controller = loader.getController();

            stringsUpdate();

            IntStream.range(0, gameBoard.maker.getHeight()).forEach(y -> IntStream.range(0, gameBoard.maker.getWidth()).forEach(x -> {
                Group group = new Group();

                //ImageViewをクリックすると追加できるように
                ImageView imageView1 = new ImageView(OwnerToImageConverter.convert(Owner.None));
                group.setOnMouseClicked(event -> {
                    Point p = new Point(x, y);
                    if (!gameBoard.enemyPlayerSet.remove(p) && gameBoard.enemyPlayerSet.size() < 2) {//もう入っているなら消す、入っていないかつ2未満しか入っていないなら追加
                        gameBoard.enemyPlayerSet.add(p);
                    }
                    this.firstViewUpdate();
                });

                //スコアの数字を読み込む
                ImageView imageView2 = new ImageView(ScoreToImageConverter.convert(gameBoard.getScore(x, y)));

                group.getChildren().addAll(imageView1, imageView2);
                controller.grid.add(group, x, y);
            }));


            //ボタンを押したら決定できるように
            controller.solveButton.setOnMouseClicked(event -> {
                if (gameBoard.decideEnemyPlayerPlace()) startNextPhase();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.firstViewUpdate();
    }

    /**
     * 敵プレイヤーの暫定位置を表示する
     */
    private void firstViewUpdate() {
        IntStream.range(0, gameBoard.maker.getHeight()).forEach(y -> IntStream.range(0, gameBoard.maker.getWidth()).forEach(x -> getTileImageViewFromGrid(x, y).setImage(OwnerToImageConverter.convert(Owner.None))));

        //プレイヤーの画像があるノードはプレイヤーの画像をImageViewごと削除
        controller.grid.getChildren().stream().map(node -> (Group) node).filter(group -> group.getChildren().size() == 3).forEach(group -> group.getChildren().remove(2));

        //プレイヤーの画像を3層目にImageViewごと追加
        AtomicInteger i = new AtomicInteger();
        Stream.concat(Arrays.stream(gameBoard.players).filter(Objects::nonNull).map(Player::getNowPoint), gameBoard.enemyPlayerSet.stream()).limit(4).forEach(point -> {
            ImageView playerView = new ImageView(images[i.getAndIncrement()]);
            getGroupFromGrid(point.x, point.y).getChildren().add(2, playerView);
        });

    }

    /**
     * コントローラーのLabelを更新する
     */
    private void stringsUpdate() {
        controller.cnt1.setText("後" + gameBoard.getRemainTurnNumber() + "ターン");
        controller.cnt2.setText("今" + (gameBoard.maker.getTurnNumber() - gameBoard.getRemainTurnNumber()) + "ターン目");

        controller.winOrLose.setText("味方：" + gameBoard.getFriendScore() + "点  敵：" + gameBoard.getEnemyScore() + "点");
    }

    /**
     * 矢印の画像を更新する
     */
    private void arrowUpdate() {
        //右の矢印を更新
        ImageView[] arr = {controller.fp1, controller.fp2, controller.ep1, controller.ep2};
        AtomicInteger i2 = new AtomicInteger();
        Arrays.stream(gameBoard.players).forEach(player -> arr[i2.getAndIncrement()].setImage(PlayerSelectToImageConverter.convert(player.isFinishNextSelect() ? player.getXyDiff().toString() : "None")));
    }

    /**
     * ゲームのメインループに入る
     */
    private void startNextPhase() {
        gameBoard.calcScore();
        stringsUpdate();
        gameBoard.solve();
        clearAndSetEventHandler();
        reView();

        controller.solveButton.setOnMouseClicked(event1 -> {
            if (gameBoard.nextStage()) {
                stringsUpdate();
                gameBoard.solve();
            }
            clearAndSetEventHandler();
            reView();
        });

        controller.moveButton.setOnMouseClicked(event2 -> {
            //ハンドラ系処理
            clearEventHandler();
            setHandlerToPutTile();
            setHandlerToMovePlayer();

            //通常モードに戻る処理
            controller.solveButton.setOnMouseClicked(event -> {
                Arrays.stream(gameBoard.players).forEach(Player::resetSelection);
                startNextPhase();
            });
        });
    }

    /**
     * GameBoardの所有者マップを基にimageViewの画像を変更する
     * プレイヤーの新しい位置に合わせて再描画する
     * プレイヤーの設定中の行動に合わせて矢印を更新する
     */
    private void reView() {
        //今のOwnerの画像に
        IntStream.range(0, gameBoard.maker.getHeight()).forEach(y -> IntStream.range(0, gameBoard.maker.getWidth()).forEach(x -> getTileImageViewFromGrid(x, y).setImage(OwnerToImageConverter.convert(gameBoard.getOwn(x, y)))));

        //プレイヤーの画像があるノードはプレイヤーの画像をImageViewごと削除
        controller.grid.getChildren().stream().filter(group -> ((Group) group).getChildren().size() == 3).forEach(group -> ((Group) group).getChildren().remove(2));
        //プレイヤーの画像をImageViewごと3層目に追加
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(gameBoard.players).forEach(player -> {
            ImageView playerView = new ImageView(images[i.getAndIncrement()]);
            getGroupFromGrid(player.getNowPoint().x, player.getNowPoint().y).getChildren().add(2, playerView);
        });

        arrowUpdate();
    }

    /**
     * 内部で関数を呼ぶだけ
     */
    private void clearAndSetEventHandler() {
        clearEventHandler();

        //敵プレイヤーをクリックして行動を選べるように設定する
        Arrays.stream(gameBoard.players).skip(2L).forEach(this::setHandlerToSelect);
    }

    /**
     * クリックイベントを全て削除する
     */
    private void clearEventHandler() {
        //全てのノードのクリックイベントを削除
        IntStream.range(0, gameBoard.maker.getHeight()).forEach(y -> IntStream.range(0, gameBoard.maker.getWidth()).forEach(x -> getGroupFromGrid(x, y).setOnMouseClicked(null)));
    }

    /**
     * 敵の動きを入力させるためにイベントハンドラを設定する
     */
    private void setHandlerToSelect(Player targetPlayer) {
        getGroupFromGrid(targetPlayer.getNowPoint().x, targetPlayer.getNowPoint().y).setOnMouseClicked(event1 -> {
            int[] diffX = {0, 1, 1, 0, -1, -1, -1, 0, 1}, diffY = {0, 0, 1, 1, 1, 0, -1, -1, -1};
            TransverseDiff[] diffTX = {TransverseDiff.Left, TransverseDiff.None, TransverseDiff.Right};
            LongitudinalDiff[] diffTY = {LongitudinalDiff.Down, LongitudinalDiff.None, LongitudinalDiff.Up};

            IntStream.range(0, 9).forEach(i -> {
                int targetY = targetPlayer.getNowPoint().y + diffY[i], targetX = targetPlayer.getNowPoint().x + diffX[i];
                //範囲内なら
                if (targetY >= 0 && targetY < gameBoard.maker.getHeight() && targetX >= 0 && targetX < gameBoard.maker.getWidth()) {
                    getGroupFromGrid(targetX, targetY).setOnMouseClicked(event2 -> {
                        targetPlayer.select(gameBoard.getOwn(targetX, targetY) == Owner.Friend ? Selection.REMOVE : Selection.MOVE, new XYDiff(diffTY[diffY[i] + 1], diffTX[diffX[i] + 1]));
                        clearAndSetEventHandler();
                        arrowUpdate();
                    });
                }
            });
        });
    }

    private void setHandlerToPutTile() {
        IntStream.range(0, gameBoard.maker.getHeight()).forEach(y -> IntStream.range(0, gameBoard.maker.getWidth()).forEach(x -> getGroupFromGrid(x, y).setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY:
                    gameBoard.setOwn(x, y, Owner.Friend);
                    break;
                case SECONDARY:
                    gameBoard.setOwn(x, y, Owner.Enemy);
                    break;
                case MIDDLE:
                    gameBoard.setOwn(x, y, Owner.None);
                    break;
            }
            reView();
        })));
    }

    private void setHandlerToMovePlayer() {
        //イベント発生側処理
        Arrays.stream(gameBoard.players).map(Player::getNowPoint).forEach(point -> {
            Group source = getGroupFromGrid(point.x, point.y);
            //DragDetectedの設定
            source.setOnDragDetected(event -> {
                //グループに対してMOVEのD&Dを設定
                Dragboard dragboard = source.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                ImageView imageView = (ImageView) source.getChildren().get(2);
                //クリップボードにはプレイヤーのイメージを入れる
                clipboardContent.putImage(imageView.getImage());
                dragboard.setContent(clipboardContent);

                event.consume();
            });
            //DragDoneの設定
            source.setOnDragDone(event -> {
                if (event.getSource() != source) {
                    source.getChildren().remove(2);
                }

                event.consume();
            });
        });

        //イベント受け入れ側処理
        IntStream.range(0, gameBoard.maker.getHeight()).boxed().flatMap(y -> IntStream.range(0, gameBoard.maker.getWidth()).boxed().map(x -> new Point(x, y)))
                .filter(point -> Arrays.stream(gameBoard.players).map(Player::getNowPoint).noneMatch(playerPoint -> playerPoint.equals(point)))
                .forEach(point -> {
                    //DragOverの設定
                    Group target = getGroupFromGrid(point.x, point.y);
                    target.setOnDragOver(event -> {
                        if (event.getGestureSource() != target && event.getDragboard().hasImage()) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }

                        event.consume();
                    });
                    //DragDroppedの設定
                    target.setOnDragDropped(event -> {
                        Dragboard dragboard = event.getDragboard();
                        boolean success = false;

                        if (dragboard.hasImage()) {
                            success = true;
                            target.getChildren().add(2, new ImageView(dragboard.getImage()));
                        }

                        event.setDropCompleted(success);

                        event.consume();
                    });
                });
    }

    /**
     * 指定した座標のタイル画像のImageViewを返す
     *
     * @param x x座標
     * @param y y座標
     * @return タイル画像が設定されるImageView
     */
    private ImageView getTileImageViewFromGrid(int x, int y) {
        return (ImageView) getGroupFromGrid(x, y).getChildren().get(0);
    }

    /**
     * 指定した座標のコンポーネント全体を囲むGroupを返す
     *
     * @param x x座標
     * @param y y座標
     * @return コンポーネントを囲むGroup
     */
    private Group getGroupFromGrid(int x, int y) {
        return (Group) controller.grid.getChildren().get(gameBoard.maker.getWidth() * y + x);
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
