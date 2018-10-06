package procon29.akashi.owners;

import javafx.scene.image.Image;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JavaFxJUnit4ClassRunner.class)
public class OwnerToImageConverterTest {
    Image noneImg = new Image("NoneTile.png");

    @Test
    public void ownerIsNoneTest() {
        assertEquals(noneImg, OwnerToImageConverter.convert(Owner.None));
    }
}