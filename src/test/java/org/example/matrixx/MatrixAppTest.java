package org.example.matrixx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitFor;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

class MatrixAppTest extends ApplicationTest {

    private TextArea logArea;
    private TextArea matrix1Display;
    private TextArea matrix2Display;
    private TextArea resultDisplay;
    private Button addButton;
    private Button subtractButton;
    private Button multiplyButton;
    private Button determinantButton1;
    private Button determinantButton2;
    private Label statusLabel;
    private Button loadMatrix1Button;
    private Button loadMatrix2Button;
    private MatrixApp matrixApp;

    @TempDir
    Path tempDir; // Temporary directory for creating test files

    @Override
    public void start(Stage stage) throws Exception {
        matrixApp = new MatrixApp();
        matrixApp.start(stage);

        // Wait for JavaFX events to settle
        waitForFxEvents();

        // Lookup UI components by their fx:id
        loadMatrix1Button = lookup("#loadMatrix1").queryAs(Button.class);
        loadMatrix2Button = lookup("#loadMatrix2").queryAs(Button.class);
        logArea = lookup("#logArea").queryAs(TextArea.class);
        matrix1Display = lookup("#matrix1Display").queryAs(TextArea.class);
        matrix2Display = lookup("#matrix2Display").queryAs(TextArea.class);
        resultDisplay = lookup("#resultDisplay").queryAs(TextArea.class);
        statusLabel = lookup("#statusLabel").queryAs(Label.class);

        addButton = lookup("#addButton").queryAs(Button.class);
        subtractButton = lookup("#subtractButton").queryAs(Button.class);
        multiplyButton = lookup("#multiplyButton").queryAs(Button.class);
        determinantButton1 = lookup("#determinantButton1").queryAs(Button.class);
        determinantButton2 = lookup("#determinantButton2").queryAs(Button.class);
    }

    @Test
    void testMatrixFiles() throws IOException {
        // Create temporary files
        Path matrix1File = tempDir.resolve("matrix1.txt");
        Path matrix2File = tempDir.resolve("matrix2.txt");

        // Write data to files
        Files.writeString(matrix1File, "1 2 3\n4 5 6\n7 8 9");
        Files.writeString(matrix2File, "9 8 7\n6 5 4\n3 2 1");

        // Simulate clicking load buttons and wait for updates
        interact(() -> matrixApp.loadMatrix(1, matrix1File.toFile()));
        interact(() -> matrixApp.loadMatrix(2, matrix2File.toFile()));

        waitForCondition(() -> !addButton.isDisabled(), 5);

        // Verify buttons are enabled
        assertFalse(addButton.isDisabled());
        assertFalse(subtractButton.isDisabled());
        assertFalse(multiplyButton.isDisabled());
    }

    @Test
    void testMatrixAdditionWithTemporaryFiles() throws IOException {
        // Create temporary files
        Path matrix1File = tempDir.resolve("matrix1.txt");
        Path matrix2File = tempDir.resolve("matrix2.txt");

        // Write data to files
        Files.writeString(matrix1File, "1 2 3\n4 5 6\n7 8 9");
        Files.writeString(matrix2File, "9 8 7\n6 5 4\n3 2 1");

        // Simulate clicking load buttons and wait for updates
        interact(() -> matrixApp.loadMatrix(1, matrix1File.toFile()));
        interact(() -> matrixApp.loadMatrix(2, matrix2File.toFile()));

        waitForCondition(() -> !addButton.isDisabled(), 5);

        // Perform addition
        interact(addButton::fire);

        // Wait for result display to update
        waitForCondition(() -> !resultDisplay.getText().isEmpty(), 5);


        // Verify addition result
        assertEquals("[10.0, 10.0, 10.0]\n[10.0, 10.0, 10.0]\n[10.0, 10.0, 10.0]", resultDisplay.getText().trim());
        assertTrue(logArea.getText().contains("Операция 'add' успешно выполнена."));
    }


    // Helper method to use waitFor with a lambda condition
    private void waitForCondition(Callable<Boolean> condition, int timeoutInSeconds) {
        try {
            waitFor(timeoutInSeconds, TimeUnit.SECONDS, condition);
        } catch (Exception e) {
            fail("Condition not met within the timeout: " + e.getMessage());
        }
    }
}
