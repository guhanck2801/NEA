module com.example.demo.nea_start {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires javafx.graphics;

    opens com.example.demo.nea_start to javafx.fxml;
    exports com.example.demo.nea_start;
}