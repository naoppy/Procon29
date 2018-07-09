import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.fail;

public class ScoreMakerTest {
    ScoreMaker maker;

    @Test(expected = IllegalArgumentException.class)
    public void heightOver12FailTest() {
        maker = new ScoreMaker(13, 11, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void widthOver12FailTest() {
        maker = new ScoreMaker(11, 13, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void heightUnder0FailTest() {
        maker = new ScoreMaker(0, 5, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void widthUnder0FailTest() {
        maker = new ScoreMaker(5, 0, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void widthMultipulHeightUnder80FailTest() {
        maker = new ScoreMaker(8,9,true,true);
    }

    @Test
    public void canNewScoreMaker() {
        maker = new ScoreMaker(10, 9, true, true);
    }

    @Test
    public void makeV_SymTrue_And_H_SymTrueTest() {
        maker = new ScoreMaker(9, 10, true, true);
        int[][] arr = maker.make();
        VSymmetryCheck(arr);
        HSymmetryCheck(arr);
    }

    /**
     * 水平な中央線で線対称になっているか判定するヘルパーメソッド
     *
     * @param arr 検証したい2次元配列
     */
    private void HSymmetryCheck(int[][] arr) {
        for (int up = 0, down = arr.length - 1; up < arr.length; up++, down--) {
            if (!Arrays.equals(arr[up], arr[down])) {
                fail();
            }
        }
    }

    /**
     * 垂直な中央線で線対称になっているか判定するヘルパーメソッド
     *
     * @param arr 検証したい2次元配列
     */
    private void VSymmetryCheck(int[][] arr) {
        for (int[] line : arr) {
            for (int left = 0, right = line.length - 1; left < line.length; left++, right--) {
                if (line[left] != line[right]) {
                    fail();
                }
            }
        }
    }

}