package procon29.akashi.gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import procon29.akashi.GameBoard;

public class Viewer {
    /**
     * GUIの基底
     */
    private BorderPane root;
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
