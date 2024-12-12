package org.example.matrixx.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.matrixx.Matrix;

/**
 * Отвечает за проверку матриц перед выполнением операций, таких как сложение,
 * вычитание, умножение и вычисление детерминанта.
 */
public class MatrixValidator {
    private static final Logger logger = LogManager.getLogger(MatrixValidator.class);

    /**
     * Проверяет, что две матрицы имеют одинаковые размеры.
     *
     * @param matrix1 первая матрица.
     * @param matrix2 вторая матрица.
     * @throws IllegalArgumentException если размеры матриц не совпадают.
     */
    public void validateEqualDimensions(Matrix matrix1, Matrix matrix2) {
        logger.debug("Проверка равенства размеров матриц.");
        if (matrix1.getRowCount() != matrix2.getRowCount() ||
                matrix1.getColumnCount() != matrix2.getColumnCount()) {
            String message = "Матрицы должны иметь одинаковые размеры для выполнения операции.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        logger.debug("Размеры матриц совпадают.");
    }

    /**
     * Проверяет, что размеры двух матриц совместимы для умножения.
     *
     * @param matrix1 первая матрица.
     * @param matrix2 вторая матрица.
     * @throws IllegalArgumentException если размеры матриц несовместимы для умножения.
     */
    public void validateMultiplicationDimensions(Matrix matrix1, Matrix matrix2) {
        logger.debug("Проверка совместимости размеров матриц для умножения.");
        if (matrix1.getColumnCount() != matrix2.getRowCount()) {
            String message = "Для умножения число столбцов первой матрицы должно быть равно числу строк второй матрицы.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        logger.debug("Размеры матриц совместимы для умножения.");
    }

    /**
     * Проверяет, что матрица является квадратной.
     *
     * @param matrix матрица для проверки.
     * @throws IllegalArgumentException если матрица не является квадратной.
     */
    public void validateSquareMatrix(Matrix matrix) {
        logger.debug("Проверка, является ли матрица квадратной.");
        if (matrix.getRowCount() != matrix.getColumnCount()) {
            String message = "Матрица должна быть квадратной для вычисления детерминанта.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        logger.debug("Матрица является квадратной.");
    }
}