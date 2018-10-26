package procon29.akashi.solver;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import procon29.akashi.GameBoard;
import procon29.akashi.players.Player;

import java.util.StringJoiner;
import java.util.stream.IntStream;

public class PrintMapForOtherSolver {
    GameBoard gameBoard;

    public PrintMapForOtherSolver(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void print() {
        StringJoiner outJoiner = new StringJoiner(System.lineSeparator());
        StringJoiner innerJoiner = new StringJoiner(" ");
        //Height Width Turn
        IntStream.of(gameBoard.maker.getHeight(), gameBoard.maker.getWidth(), gameBoard.getRemainTurnNumber())
                .boxed()
                .map(String::valueOf)
                .forEach(innerJoiner::add);
        outJoiner.add(innerJoiner.toString());
        //P1y P2y
        for (Player player : gameBoard.players) {
            innerJoiner = new StringJoiner(" ");
            outJoiner.add(innerJoiner.add(String.valueOf(player.getNowPoint().y)).add(String.valueOf(player.getNowPoint().x)).toString());
        }
        //score
        for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
            innerJoiner = new StringJoiner(" ");
            for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                innerJoiner.add(String.valueOf(gameBoard.getScore(x, y)));
            }
            outJoiner.add(innerJoiner.toString());
        }
        //owner
        for (int y = 0; y < gameBoard.maker.getHeight(); y++) {
            innerJoiner = new StringJoiner("");
            for (int x = 0; x < gameBoard.maker.getWidth(); x++) {
                switch (gameBoard.getOwn(x, y)) {
                    case Friend:
                        innerJoiner.add("F");
                        break;
                    case Enemy:
                        innerJoiner.add("E");
                        break;
                    case None:
                        innerJoiner.add("N");
                        break;
                }
            }
            outJoiner.add(innerJoiner.toString());
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(outJoiner.toString());
        clipboard.setContent(clipboardContent);
        System.err.println("solved");
    }
}
