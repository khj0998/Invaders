package screen;

import engine.Core;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class SkinSelectionScreenTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getColorCode_1P() {
        assertAll(
                () -> assertEquals(0, SkinSelectionScreen.getColorCode_1P())
        );
    }

    @Test
    void getColorCode_2P() {
        assertAll(
                () -> assertEquals(0, SkinSelectionScreen.getColorCode_2P())
                );
    }
}