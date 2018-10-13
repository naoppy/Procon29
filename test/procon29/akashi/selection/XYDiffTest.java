package procon29.akashi.selection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XYDiffTest {
    XYDiff xfDiff;

    @Test
    public void ToStringTest() {
        xfDiff = new XYDiff(LongitudinalDiff.Up, TransverseDiff.Right);
        assertEquals("UR", xfDiff.toString());

        xfDiff = new XYDiff(LongitudinalDiff.Up, TransverseDiff.Left);
        assertEquals("UL", xfDiff.toString());

        xfDiff = new XYDiff(LongitudinalDiff.Up, TransverseDiff.None);
        assertEquals("UN", xfDiff.toString());

        xfDiff = new XYDiff(LongitudinalDiff.Down, TransverseDiff.Left);
        assertEquals("DL", xfDiff.toString());

        xfDiff = new XYDiff(LongitudinalDiff.None, TransverseDiff.Left);
        assertEquals("NL", xfDiff.toString());

        xfDiff = new XYDiff(LongitudinalDiff.None, TransverseDiff.None);
        assertEquals("NN", xfDiff.toString());
    }

}