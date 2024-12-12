package org.example.matrixx;

import org.example.matrixx.calculator.MatrixCalculator;
import org.example.matrixx.calculator.MatrixValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MatrixCalculatorTest {
    private final MatrixValidator validator = new MatrixValidator();
    private final MatrixCalculator calculator = new MatrixCalculator(validator);

    @Test
    void testAddition() {
        Matrix matrix1 = new Matrix(new double[][]{
                {1, 2},
                {3, 4}
        });

        Matrix matrix2 = new Matrix(new double[][]{
                {5, 6},
                {7, 8}
        });

        Matrix result = calculator.add(matrix1, matrix2);

        assertArrayEquals(new double[][]{
                {6, 8},
                {10, 12}
        }, result.toArray());
    }

    @Test
    void testSubtraction() {
        Matrix matrix1 = new Matrix(new double[][]{
                {5, 7},
                {9, 11}
        });

        Matrix matrix2 = new Matrix(new double[][]{
                {1, 3},
                {2, 4}
        });

        Matrix result = calculator.subtract(matrix1, matrix2);

        assertArrayEquals(new double[][]{
                {4, 4},
                {7, 7}
        }, result.toArray());
    }

    @Test
    void testMultiplication() {
        Matrix matrix1 = new Matrix(new double[][]{
                {1, 2},
                {3, 4}
        });

        Matrix matrix2 = new Matrix(new double[][]{
                {2, 0},
                {1, 3}
        });

        Matrix result = calculator.multiply(matrix1, matrix2);

        assertArrayEquals(new double[][]{
                {4, 6},
                {10, 12}
        }, result.toArray());
    }

    @Test
    void testDeterminant() {
        Matrix matrix = new Matrix(new double[][]{
                {4, 3},
                {3, 2}
        });

        double determinant = calculator.determinant(matrix);

        assertEquals(-1, determinant);
    }

    @Test
    void testAdditionWithInvalidDimensions() {
        Matrix matrix1 = new Matrix(new double[][]{
                {1, 2},
                {3, 4}
        });

        Matrix matrix2 = new Matrix(new double[][]{
                {1, 2, 3}
        });

        assertThrows(IllegalArgumentException.class, () -> calculator.add(matrix1, matrix2));
    }
}