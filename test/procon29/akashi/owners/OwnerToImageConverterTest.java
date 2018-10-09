package procon29.akashi.owners;

import javafx.scene.image.Image;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JavaFxJUnit4ClassRunner.class)
public class OwnerToImageConverterTest {

    @Test
    public void SamePointerReturnTest() {
        assertEquals(OwnerToImageConverter.convert(Owner.None), OwnerToImageConverter.convert(Owner.None));

        assertEquals(OwnerToImageConverter.convert(Owner.Friend), OwnerToImageConverter.convert(Owner.Friend));

        assertEquals(OwnerToImageConverter.convert(Owner.Enemy), OwnerToImageConverter.convert(Owner.Enemy));
    }
}