import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

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

    public Parent getView() {
        return new AnchorPane();
    }

    private static String inputCode() {
        System.out.println("QRデータを入れて");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("[A-z]");
        return sc.next();
    }
}
