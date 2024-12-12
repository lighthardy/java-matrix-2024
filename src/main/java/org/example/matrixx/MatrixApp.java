package org.example.matrixx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.matrixx.calculator.MatrixCalculator;
import org.example.matrixx.calculator.MatrixValidator;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX application for performing matrix operations such as addition,
 * subtraction, multiplication, and determinant calculation.
 */
/**
 * Приложение JavaFX для выполнения операций с матрицами,
 * таких как сложение, вычитание, умножение и вычисление детерминанта.
 */
public class MatrixApp extends Application {
    private static final Logger logger = LogManager.getLogger(MatrixApp.class);

    private final MatrixValidator validator = new MatrixValidator();
    private final MatrixCalculator calculator = new MatrixCalculator(validator);

    private Matrix matrix1;
    private Matrix matrix2;

    // Текстовые области для отображения матриц и логов
    private final TextArea logArea = new TextArea();
    private final TextArea matrix1Display = new TextArea();
    private final TextArea matrix2Display = new TextArea();
    private final TextArea resultDisplay = new TextArea();
    private final Label statusLabel = new Label("Добро пожаловать! Загрузите матрицы для начала работы.");

    // Кнопки для операций с матрицами
    private final Button addButton = new Button("Сложение");
    private final Button subtractButton = new Button("Вычитание");
    private final Button multiplyButton = new Button("Умножение");
    private final Button determinantButton1 = new Button("Детерминант (Матрица 1)");
    private final Button determinantButton2 = new Button("Детерминант (Матрица 2)");

    /**
     * Запускает приложение JavaFX и настраивает интерфейс.
     *
     * @param primaryStage главный этап приложения
     */
    @Override
    public void start(Stage primaryStage) {
        logger.info("Запуск MatrixApp...");

        // Set IDs for TestFX testing
        logArea.setId("logArea");
        matrix1Display.setId("matrix1Display");
        matrix2Display.setId("matrix2Display");
        resultDisplay.setId("resultDisplay");
        statusLabel.setId("statusLabel");
        addButton.setId("addButton");
        subtractButton.setId("subtractButton");
        multiplyButton.setId("multiplyButton");
        determinantButton1.setId("determinantButton1");
        determinantButton2.setId("determinantButton2");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Build UI
        HBox fileSelectionBox = createFileSelectionBox();
        HBox operationButtons = createOperationButtons();
        HBox displays = createMatrixAndResultDisplays();
        VBox logAndStatus = createLogAndStatusSection();

        root.getChildren().addAll(fileSelectionBox, operationButtons, displays, logAndStatus);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Операции с Матрицами");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Disable operation buttons until matrices are loaded
        setOperationButtonsEnabled(false);

        logger.info("MatrixApp успешно запущено.");
    }

    /**
     * Создает секцию для выбора файлов матриц.
     *
     * @return HBox с кнопками для загрузки матриц
     */
    private HBox createFileSelectionBox() {
        logger.debug("Создание секции выбора файлов...");
        Button loadMatrix1Button = new Button("Загрузить Матрицу 1");
        Button loadMatrix2Button = new Button("Загрузить Матрицу 2");

        loadMatrix1Button.setId("loadMatrix1");
        loadMatrix2Button.setId("loadMatrix2");
        loadMatrix1Button.setOnAction(e -> loadMatrix(1,null));
        loadMatrix2Button.setOnAction(e -> loadMatrix(2, null));

        return new HBox(10, loadMatrix1Button, loadMatrix2Button);
    }

    /**
     * Создает секцию кнопок для операций с матрицами.
     *
     * @return HBox с кнопками операций
     */
    private HBox createOperationButtons() {
        logger.debug("Создание кнопок операций...");
        addButton.setOnAction(e -> performOperation("add"));
        subtractButton.setOnAction(e -> performOperation("subtract"));
        multiplyButton.setOnAction(e -> performOperation("multiply"));
        determinantButton1.setOnAction(e -> calculateDeterminant(1));
        determinantButton2.setOnAction(e -> calculateDeterminant(2));

        return new HBox(10, addButton, subtractButton, multiplyButton, determinantButton1, determinantButton2);
    }

    /**
     * Создает секцию для отображения матриц и результатов.
     *
     * @return HBox с областями отображения
     */
    private HBox createMatrixAndResultDisplays() {
        VBox matrix1Box = createMatrixDisplayBox("Матрица 1", matrix1Display);
        VBox matrix2Box = createMatrixDisplayBox("Матрица 2", matrix2Display);
        VBox resultBox = createMatrixDisplayBox("Результат", resultDisplay);

        return new HBox(10, matrix1Box, matrix2Box, resultBox);
    }

    /**
     * Создает область отображения для одной матрицы или результата.
     *
     * @param title   название области
     * @param display TextArea для отображения содержимого
     * @return VBox с областью отображения
     */
    private VBox createMatrixDisplayBox(String title, TextArea display) {
        display.setEditable(false);
        ScrollPane scrollPane = new ScrollPane(display);
        scrollPane.setFitToWidth(true);
        Label label = new Label(title);
        return new VBox(5, label, scrollPane);
    }

    /**
     * Создает секцию для логов и статуса.
     *
     * @return VBox с текстовой областью логов и статусом
     */
    private VBox createLogAndStatusSection() {
        logArea.setEditable(false);
        ScrollPane logScrollPane = new ScrollPane(logArea);
        logScrollPane.setFitToWidth(true);
        logScrollPane.setPrefHeight(100);
        return new VBox(10, logScrollPane, statusLabel);
    }

    /**
     * Loads a matrix from a file and updates the corresponding display area.
     * This method can use a directly provided file for testing purposes.
     *
     * @param matrixNumber The number of the matrix (1 or 2) to load.
     * @param file         The file to load the matrix from. If null, a FileChooser will be used.
     */
    protected void loadMatrix(int matrixNumber, File file) {
        if (file == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл матрицы");
            file = fileChooser.showOpenDialog(null);
        }

        if (file == null) {
            log("Загрузка матрицы отменена.");
            return;
        }

        try {
            logger.info("Загрузка матрицы из файла: {}", file.getAbsolutePath());
            Matrix matrix = MatrixFileReader.readFromFile(file.getAbsolutePath());
            if (matrixNumber == 1) {
                matrix1 = matrix;
                matrix1Display.setText(matrix.toString());
                log("Матрица 1 успешно загружена.");
            } else {
                matrix2 = matrix;
                matrix2Display.setText(matrix.toString());
                log("Матрица 2 успешно загружена.");
            }
            statusLabel.setText("Матрицы загружены. Готово к операциям.");
            setOperationButtonsEnabled(true);
        } catch (InvalidMatrixFormatException e) {
            log("Неверный формат матрицы: " + e.getMessage());
            logger.error("Ошибка формата матрицы", e);
            showErrorDialog("Ошибка загрузки матрицы", "Файл матрицы неверного формата: " + e.getMessage());
        } catch (IOException e) {
            log("Ошибка чтения файла матрицы: " + e.getMessage());
            logger.error("Ошибка чтения файла", e);
            showErrorDialog("Ошибка чтения файла", "Произошла ошибка при чтении файла: " + e.getMessage());
        }
    }

    /**
     * Выполняет операцию с матрицами и отображает результат.
     *
     * @param operation операция для выполнения ("add", "subtract", "multiply").
     */
    private void performOperation(String operation) {
        if (matrix1 == null || matrix2 == null) {
            log("Операция не выполнена: матрицы не загружены.");
            statusLabel.setText("Пожалуйста, загрузите обе матрицы перед выполнением операций.");
            return;
        }

        try {
            logger.info("Выполнение операции: {}", operation);
            Matrix result = switch (operation) {
                case "add" -> calculator.add(matrix1, matrix2);
                case "subtract" -> calculator.subtract(matrix1, matrix2);
                case "multiply" -> calculator.multiply(matrix1, matrix2);
                default -> throw new UnsupportedOperationException("Неизвестная операция: " + operation);
            };
            resultDisplay.setText(result.toString());
            log("Операция '" + operation + "' успешно выполнена.");
        } catch (IllegalArgumentException e) {
            log("Операция не выполнена: " + e.getMessage());
            logger.error("Ошибка операции", e);
            showErrorDialog("Ошибка операции", e.getMessage());
        }
    }

    /**
     * Вычисляет детерминант выбранной матрицы.
     *
     * @param matrixNumber номер матрицы (1 или 2) для вычисления детерминанта.
     */
    private void calculateDeterminant(int matrixNumber) {
        try {
            logger.info("Вычисление детерминанта для Матрицы {}", matrixNumber);
            if (matrixNumber == 1 && matrix1 != null) {
                double determinant = calculator.determinant(matrix1);
                resultDisplay.setText("Детерминант Матрицы 1: " + determinant);
                log("Детерминант Матрицы 1 вычислен: " + determinant);
            } else if (matrixNumber == 2 && matrix2 != null) {
                double determinant = calculator.determinant(matrix2);
                resultDisplay.setText("Детерминант Матрицы 2: " + determinant);
                log("Детерминант Матрицы 2 вычислен: " + determinant);
            } else {
                log("Вычисление детерминанта не выполнено: матрица не загружена.");
                statusLabel.setText("Пожалуйста, загрузите матрицу перед вычислением детерминанта.");
            }
        } catch (IllegalArgumentException e) {
            log("Ошибка вычисления детерминанта: " + e.getMessage());
            logger.error("Ошибка вычисления детерминанта", e);
            showErrorDialog("Ошибка вычисления детерминанта", e.getMessage());
        }
    }

    /**
     * Логгирует сообщение и добавляет его в текстовую область для логов.
     *
     * @param message сообщение для логгирования.
     */
    private void log(String message) {
        logger.info(message);
        logArea.appendText(message + "\n");
    }

    /**
     * Отображает диалоговое окно с ошибкой.
     *
     * @param title   заголовок окна.
     * @param message сообщение об ошибке.
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Включает или отключает кнопки операций.
     *
     * @param enabled true, чтобы включить кнопки, false - чтобы отключить.
     */
    private void setOperationButtonsEnabled(boolean enabled) {
        addButton.setDisable(!enabled);
        subtractButton.setDisable(!enabled);
        multiplyButton.setDisable(!enabled);
        determinantButton1.setDisable(!enabled);
        determinantButton2.setDisable(!enabled);
    }


}
