package org.example.matrixx;

import java.util.Arrays;

import java.util.Arrays;
import java.util.Objects;

/**
 * Представляет матрицу и предоставляет методы для доступа и работы с её данными.
 */
public class Matrix {
    private final double[][] data;

    /**
     * Создает матрицу на основе двумерного массива.
     *
     * @param data двумерный массив, представляющий матрицу.
     */
    public Matrix(double[][] data) {
        // Создаем глубокую копию массива, чтобы избежать изменения исходных данных.
        this.data = Arrays.stream(data)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }

    /**
     * Возвращает количество строк в матрице.
     *
     * @return количество строк.
     */
    public int getRowCount() {
        return data.length;
    }

    /**
     * Возвращает количество столбцов в матрице.
     *
     * @return количество столбцов.
     */
    public int getColumnCount() {
        return data[0].length;
    }

    /**
     * Получает значение элемента матрицы по заданным индексу строки и столбца.
     *
     * @param row индекс строки.
     * @param col индекс столбца.
     * @return значение элемента матрицы.
     */
    public double get(int row, int col) {
        return data[row][col];
    }

    /**
     * Устанавливает значение элемента матрицы по заданным индексу строки и столбца.
     *
     * @param row   индекс строки.
     * @param col   индекс столбца.
     * @param value значение, которое нужно установить.
     */
    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    /**
     * Преобразует матрицу в двумерный массив.
     *
     * @return глубокая копия матрицы в виде двумерного массива.
     */
    public double[][] toArray() {
        return Arrays.stream(data)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }

    /**
     * Возвращает строковое представление матрицы.
     *
     * @return строка, представляющая матрицу.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : data) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix matrix)) return false;
        return Objects.deepEquals(data, matrix.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }
}

