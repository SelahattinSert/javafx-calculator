module com.example.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;
    requires java.scripting;

    opens com.example.calculator to javafx.fxml;
    exports com.example.calculator;
    exports com.example.calculator.util;
    opens com.example.calculator.util to javafx.fxml;
    exports com.example.calculator.controller;
    opens com.example.calculator.controller to javafx.fxml;
}