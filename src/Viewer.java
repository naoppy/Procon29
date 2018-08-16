import javafx.scene.Parent;

/**
 * ゲームの進行を管理する最上位クラス
 */
public class Viewer {
    ScoreMaker maker = new ScoreFromQR("");

    Person[] players = {new Person(true), new Person(true), new Person(false), new Person(false)};

    Owner[][] map = new Owner[maker.getHeight()][maker.getWidth()];

    int[][] scores = maker.getMap();

    public Parent getView() {
        return null;
    }
}
