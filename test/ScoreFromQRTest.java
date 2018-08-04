import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreFromQRTest {
    ScoreMaker maker;

    String sampleRightQR = "8 11:" +
            "-2 1 0 1 2 0 2 1 0 1 -2:" +
            "1 3 2 -2 0 1 0 -2 2 3 1:" +
            "1 3 2 1 0 -2 0 1 2 3 1:" +
            "2 1 1 2 2 3 2 2 1 1 2:" +
            " 2 1 1 2 2 3 2 2 1 1 2:" +
            " 1 3 2 1 0 -2 0 1 2 3 1: " +
            "1 3 2 -2 0 1 0 -2 2 3 1:" +
            " -2 1 0 1 2 0 2 1 0 1 -2:" +
            "2 2:7 10:";

    @Test
    public void canMakeNormalInputAndReturnNonZeroArrayTest() {
        maker = new ScoreFromQR(sampleRightQR);

        int[][] scores = maker.getMap();

        for (int[] arr : scores) {
            if (arr.equals(new int[arr.length])) {
                fail();
            }
        }
    }

    @Test
    public void canGetWidthAndHeightTest() {
        maker = new ScoreFromQR(sampleRightQR);

        assertEquals(8, maker.getHeight());
        assertEquals(11, maker.getWidth());
    }

    @Test
    public void canGetRightScoreArrayTest() {
        maker = new ScoreFromQR(sampleRightQR);

        assertArrayEquals(new int[]{-2, 1, 0, 1, 2, 0, 2, 1, 0, 1, -2}, maker.getMap()[0]);
        assertArrayEquals(new int[]{1, 3, 2, -2, 0, 1, 0, -2, 2, 3, 1}, maker.getMap()[6]);
        assertArrayEquals(new int[]{-2, 1, 0, 1, 2, 0, 2, 1, 0, 1, -2}, maker.getMap()[7]);
    }
}