package procon29.akashi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
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
public class Viewer {
    ScoreMaker maker = new ScoreFromQR(Viewer.inputCode());

    int[][] scores = maker.getMap();

    Owner[][] map = new Owner[scores.length][scores[0].length];

    RedTeamPlayer rp1 = new RedTeamPlayer(), rp2 = new RedTeamPlayer();
    BlueTeamPlayer bp1 = new BlueTeamPlayer(), bp2 = new BlueTeamPlayer();

    BorderPane root;

    {
        try {
            root = FXMLLoader.load(getClass().getResource("root2.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getView() {
        return root;
    }

    private static String inputCode() {
        System.out.println("QRデータを入れて");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("[A-z]");
        return sc.next();
    }
}
