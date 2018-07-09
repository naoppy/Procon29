import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreFromQRTest {
    ScoreFromQR maker;

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

        int[][] scores = maker.make();

        for(int[] arr : scores) {
            if(arr.equals(new int[arr.length])) {
                fail();
            }
        }
    }

    @Test
    public void canGetWidthAndHeightTest() {
        maker = new ScoreFromQR(sampleRightQR);
        maker.make();

        assertEquals(8,maker.getHeight());
        assertEquals(11,maker.getWidth());
    }
}