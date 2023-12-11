package engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screen.SkinSelectionScreen;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateskincodeOfCurrentPlayer() throws IOException {
        assertAll(
                () -> assertEquals(1, Core.getFileManager().getCurrentPlayer().getSkincode()),
                () -> {for(int i = 1; i < 7; i++) {
                    if (Core.getFileManager().getCurrentPlayer().getSkincode() < 7) {
                        Core.getFileManager().updateskincodeOfCurrentPlayer();
                        assertEquals(i, Core.getFileManager().getCurrentPlayer().getSkincode());
                    }
                }
                }
        );
    }
}