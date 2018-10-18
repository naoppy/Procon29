package procon29.akashi;

import procon29.akashi.owners.Owner;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * チームの点数を計算するクラス
 */
public class AllScoreCalc {
    private AllScoreCalc() {
    }

    /**
     * タイル点と囲い点を足し合わせた総得点を返す
     * @param board 盤面
     * @param targetTeam 点数を計算するチーム
     * @return 点数
     */
    public static int calc(GameBoard board, Owner targetTeam) {
        int h = board.maker.getHeight(), w = board.maker.getWidth();

        //所有しているタイルの点数を加算
        int score = IntStream.range(0, h).boxed().flatMap(y -> IntStream.range(0, w).boxed().map(x -> new Point(x, y))).filter(p -> board.getOwn(p.x, p.y) == targetTeam).mapToInt(p -> board.getScore(p.x, p.y)).sum();

        //囲い点を計算
        SurroundedScoreCalc calc = new SurroundedScoreCalc(board, targetTeam);
        score += IntStream.range(0, h).boxed().flatMap(y -> IntStream.range(0, w).boxed().map(x -> new Point(x, y))).filter(p -> board.getOwn(p.x, p.y) != targetTeam).mapToInt(p -> calc.calcSurroundScore(p.y, p.x)).sum();

        return score;
    }
}
