package procon29.akashi;

import procon29.akashi.owners.Owner;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

class ScoreCalc {
    //8近傍移動用
    static private int[] dx = {0, 1, 1, 1, 0, -1, -1, -1}, dy = {1, 1, 0, -1, -1, -1, 0, 1};

    //探索用配列初期化
    private boolean[][] used;

    private GameBoard board;

    private Owner targetTeam;

    ScoreCalc(GameBoard gameBoard, Owner targetTeam) {
        this.board = gameBoard;
        this.used = new boolean[board.maker.getHeight()][board.maker.getWidth()];
        this.targetTeam = targetTeam;
    }

    int calcSurroundScore(int startY, int startX) {
        //キューを作成して座標点を入れる
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startY, startX));

        int score = Math.abs(board.getScore(startX, startY));
        boolean isSurrounded = false;
        used[startY][startX] = true;

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            for (int diffY : dy) {
                for (int diffX : dx) {
                    int newY = p.x + diffY, newX = p.y + diffX;
                    if (newX < 0 || newY < 0 || newX >= board.maker.getWidth() || newY >= board.maker.getHeight()) {
                        isSurrounded = true;
                        continue;
                    }
                    if (board.getOwn(newX, newY) == targetTeam || used[newY][newX]) {
                        continue;
                    }

                    used[newY][newX] = true;
                    score += Math.abs(board.getScore(newX, newY));
                    queue.add(new Point(newY, newX));
                }
            }
        }

        if (isSurrounded) {
            return 0;
        } else {
            return score;
        }
    }
}
