package procon29.akashi.scores;

import java.util.Scanner;

public class QRInputer {
    /**
     * インスタンス化できない
     */
    private QRInputer(){};

    /**
     * QRコードの入力を受け付ける
     *
     * @return QRコード
     */
    public static String inputCode() {
        System.out.println("QRデータを入れてください");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("[A-z]");
        return sc.next();
    }
}
