package org.example.matrixx;

/**
 * Исключение, указывающее на ошибку при выполнении операции с матрицей.
 */
public class MatrixOperationException extends Exception {

    /**
     * Создает исключение с заданным сообщением.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public MatrixOperationException(String message) {
        super(message);
    }
}