package org.example.matrixx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитный класс для чтения матриц из файлов.
 */
public class MatrixFileReader {
    private static final Logger logger = LogManager.getLogger(MatrixFileReader.class);

    /**
     * Читает матрицу из файла с проверкой формата и содержимого.
     *
     * @param filePath путь к файлу.
     * @return матрица, прочитанная из файла.
     * @throws InvalidMatrixFormatException если формат файла некорректен.
     * @throws IOException                  если возникла ошибка при чтении файла.
     */
    public static Matrix readFromFile(String filePath) throws InvalidMatrixFormatException, IOException {
        logger.info("Попытка чтения матрицы из файла: {}", filePath);

        List<double[]> rows = new ArrayList<>();
        int expectedColumns = -1; // Ожидаемое количество столбцов в строках.

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                logger.debug("Обработка строки {}: {}", lineNumber, line.trim());

                // Разделяем строку на токены (числа).
                String[] tokens = line.trim().split("\\s+");

                // Проверяем длину строки.
                if (expectedColumns == -1) {
                    expectedColumns = tokens.length;
                    logger.debug("Установлено ожидаемое количество столбцов: {}", expectedColumns);
                } else if (tokens.length != expectedColumns) {
                    String errorMessage = String.format(
                            "Несоответствие длины строки в строке %d. Ожидалось %d столбцов, но получено %d.",
                            lineNumber, expectedColumns, tokens.length
                    );
                    logger.error(errorMessage);
                    throw new InvalidMatrixFormatException(errorMessage);
                }

                // Парсинг строки в массив чисел.
                double[] row = new double[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    try {
                        row[i] = Double.parseDouble(tokens[i]);
                    } catch (NumberFormatException e) {
                        String errorMessage = String.format(
                                "Некорректный формат числа в строке %d, столбце %d: '%s'.",
                                lineNumber, i + 1, tokens[i]
                        );
                        logger.error(errorMessage, e);
                        throw new InvalidMatrixFormatException(errorMessage, e);
                    }
                }

                rows.add(row);
            }

            // Проверяем, что файл не пустой.
            if (rows.isEmpty()) {
                String errorMessage = "Файл пуст или не содержит допустимых строк.";
                logger.error(errorMessage);
                throw new InvalidMatrixFormatException(errorMessage);
            }

        } catch (IOException e) {
            logger.error("Ошибка чтения файла: {}", filePath, e);
            throw e;
        }

        logger.info("Матрица успешно прочитана из файла: {}", filePath);
        return new Matrix(rows.toArray(new double[0][]));
    }
}
