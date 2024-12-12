package org.example.matrixx.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.matrixx.Matrix;

/**
 * Предоставляет методы для выполнения различных операций с матрицами, таких как сложение,
 * вычитание, умножение и вычисление детерминанта.
 */
public class MatrixCalculator {
    private static final Logger logger = LogManager.getLogger(MatrixCalculator.class);

    private final MatrixValidator validator;

    /**
     * Конструктор класса MatrixCalculator.
     *
     * @param validator экземпляр MatrixValidator для проверки корректности матриц.
     */
    public MatrixCalculator(MatrixValidator validator) {
        this.validator = validator;
    }

    /**
     * Складывает две матрицы поэлементно.
     *
     * @param matrix1 первая матрица.
     * @param matrix2 вторая матрица.
     * @return результирующая матрица после сложения.
     */
    public Matrix add(Matrix matrix1, Matrix matrix2) {
        logger.info("Начало сложения двух матриц.");
        validator.validateEqualDimensions(matrix1, matrix2); // Проверка размеров матриц.

        int rows = matrix1.getRowCount();
        int cols = matrix1.getColumnCount();
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1.get(i, j) + matrix2.get(i, j); // Складываем элементы.
            }
        }

        logger.info("Сложение успешно завершено.");
        return new Matrix(result);
    }

    /**
     * Вычитает вторую матрицу из первой поэлементно.
     *
     * @param matrix1 первая матрица.
     * @param matrix2 вторая матрица.
     * @return результирующая матрица после вычитания.
     */
    public Matrix subtract(Matrix matrix1, Matrix matrix2) {
        logger.info("Начало вычитания двух матриц.");
        validator.validateEqualDimensions(matrix1, matrix2); // Проверка размеров матриц.

        int rows = matrix1.getRowCount();
        int cols = matrix1.getColumnCount();
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1.get(i, j) - matrix2.get(i, j); // Вычитаем элементы.
            }
        }

        logger.info("Вычитание успешно завершено.");
        return new Matrix(result);
    }

    /**
     * Умножает две матрицы.
     *
     * @param matrix1 первая матрица.
     * @param matrix2 вторая матрица.
     * @return результирующая матрица после умножения.
     */
    public Matrix multiply(Matrix matrix1, Matrix matrix2) {
        logger.info("Начало умножения двух матриц.");
        validator.validateMultiplicationDimensions(matrix1, matrix2); // Проверка совместимости размеров.

        int rows = matrix1.getRowCount();
        int cols = matrix2.getColumnCount();
        int commonDim = matrix1.getColumnCount();

        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < commonDim; k++) {
                    result[i][j] += matrix1.get(i, k) * matrix2.get(k, j); // Суммируем произведения элементов.
                }
            }
        }

        logger.info("Умножение успешно завершено.");
        return new Matrix(result);
    }

    /**
     * Вычисляет детерминант квадратной матрицы.
     *
     * @param matrix матрица, для которой вычисляется детерминант.
     * @return значение детерминанта.
     */
    public double determinant(Matrix matrix) {
        logger.info("Начало вычисления детерминанта для матрицы.");
        validator.validateSquareMatrix(matrix); // Проверка на квадратность матрицы.

        double result = calculateDeterminantRecursive(matrix.toArray()); // Рекурсивный метод для вычисления.
        logger.info("Вычисление детерминанта завершено. Значение: {}", result);
        return result;
    }

    // Вспомогательные методы

    /**
     * Рекурсивно вычисляет детерминант матрицы.
     *
     * @param matrix двумерный массив, представляющий матрицу.
     * @return детерминант матрицы.
     */
    private double calculateDeterminantRecursive(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) { // Базовый случай: матрица 1x1.
            return matrix[0][0];
        }

        double determinant = 0;
        for (int i = 0; i < n; i++) {
            double[][] subMatrix = createSubMatrix(matrix, i); // Создаем подматрицу.
            determinant += Math.pow(-1, i) * matrix[0][i] * calculateDeterminantRecursive(subMatrix); // Рекурсивное вычисление.
        }
        return determinant;
    }

    /**
     * Создает подматрицу путем исключения первой строки и заданного столбца.
     *
     * @param matrix исходная матрица.
     * @param excludedCol индекс столбца, который исключается.
     * @return новая подматрица.
     */
    private double[][] createSubMatrix(double[][] matrix, int excludedCol) {
        int n = matrix.length;
        double[][] subMatrix = new double[n - 1][n - 1];

        for (int i = 1; i < n; i++) {
            int colIndex = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludedCol) continue; // Пропускаем исключаемый столбец.
                subMatrix[i - 1][colIndex++] = matrix[i][j]; // Копируем элементы.
            }
        }

        return subMatrix;
    }
}

