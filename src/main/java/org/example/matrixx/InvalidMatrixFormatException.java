package org.example.matrixx;

/**
 * Исключение, указывающее на ошибку формата матрицы при чтении из файла.
 */
public class InvalidMatrixFormatException extends Exception {

    /**
     * Создает исключение с заданным сообщением.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public InvalidMatrixFormatException(String message) {
        super(message);
    }

    /**
     * Создает исключение с заданным сообщением и исходной причиной.
     *
     * @param message сообщение, описывающее причину исключения.
     * @param cause   исходная причина исключения.
     */
    public InvalidMatrixFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}