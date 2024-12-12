module org.example.matrixx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens org.example.matrixx to javafx.fxml;
    exports org.example.matrixx;
}