package procon29.akashi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import procon29.akashi.players.BlueTeamPlayer;
import procon29.akashi.players.RedTeamPlayer;
import procon29.akashi.scores.ScoreFromQR;
import procon29.akashi.scores.ScoreMaker;

import java.io.IOException;
import java.util.Scanner;

/**
 * ゲームの進行を管理する最上位クラス
 */
public class GameBoard {
    /**
     * スコアと自分の位置を与える
     */
    private ScoreMaker maker = new ScoreFromQR(GameBoard.inputCode());
    /**
     * スコア
     */
    private int[][] scores = maker.getMap();
    /**
     * どのチームの領域か
     */
    private Owner[][] map = new Owner[maker.getHeight()][maker.getWidth()];
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
     * GUIをFXMLから読み込んで生成
     */
    public GameBoard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/root2.fxml"));
            root = loader.load();
            controller = loader.getController();

            for(int y = 0; y < maker.getHeight(); y++) {
                for(int x = 0; x < maker.getWidth(); x++) {
                    ImageView imageView = new ImageView("NoneTile.png");
                    controller.grid.add(imageView, y, x);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
     * QRコードの入力を受け付ける
     *
     * @return QRコード
     */
    private static String inputCode() {
        System.out.println("QRデータを入れて");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("[A-z]");
        return sc.next();
    }
}
