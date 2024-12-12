package org.example.matrixx;

import static org.junit.jupiter.api.Assertions.*;

import org.example.matrixx.Matrix;
import org.example.matrixx.MatrixFileReader;
import org.example.matrixx.InvalidMatrixFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MatrixFileReaderTest {

    @TempDir
    Path tempDir; // Создает временную директорию для тестовых файлов.

    @Test
    void testValidMatrixFile() throws IOException, InvalidMatrixFormatException {
        Path file = tempDir.resolve("valid_matrix.txt");
        Files.writeString(file, "1.0 2.0 3.0\n4.0 5.0 6.0\n7.0 8.0 9.0");

        Matrix matrix = MatrixFileReader.readFromFile(file.toString());

        assertNotNull(matrix);
        assertEquals(3, matrix.getRowCount());
        assertEquals(3, matrix.getColumnCount());
        assertArrayEquals(new double[][]{
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        }, matrix.toArray());
    }

    @Test
    void testEmptyFile() {
        Path file = tempDir.resolve("empty_file.txt");
        assertThrows(IOException.class, () -> Files.writeString(file, ""));
        assertThrows(InvalidMatrixFormatException.class, () -> MatrixFileReader.readFromFile(file.toString()));
    }

    @Test
    void testInvalidNumberFormat() throws IOException {
        Path file = tempDir.resolve("invalid_number.txt");
        Files.writeString(file, "1.0 2.0 3.0\n4.0 5.0 abc\n7.0 8.0 9.0");

        InvalidMatrixFormatException exception = assertThrows(InvalidMatrixFormatException.class,
                () -> MatrixFileReader.readFromFile(file.toString()));

        assertTrue(exception.getMessage().contains("Некорректный формат числа"));
    }

    @Test
    void testInconsistentRowLengths() throws IOException {
        Path file = tempDir.resolve("inconsistent_rows.txt");
        Files.writeString(file, "1.0 2.0 3.0\n4.0 5.0\n7.0 8.0 9.0");

        InvalidMatrixFormatException exception = assertThrows(InvalidMatrixFormatException.class,
                () -> MatrixFileReader.readFromFile(file.toString()));

        assertTrue(exception.getMessage().contains("Несоответствие длины строки"));
    }

    @Test
    void testValidMatrixFileWithExtraSpaces() throws IOException, InvalidMatrixFormatException {
        Path file = tempDir.resolve("valid_matrix_with_spaces.txt");
        Files.writeString(file, " 1.0   2.0 3.0 \n 4.0 5.0 6.0 \n 7.0  8.0  9.0 ");

        Matrix matrix = MatrixFileReader.readFromFile(file.toString());

        assertNotNull(matrix);
        assertEquals(3, matrix.getRowCount());
        assertEquals(3, matrix.getColumnCount());
        assertArrayEquals(new double[][]{
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        }, matrix.toArray());
    }

}
