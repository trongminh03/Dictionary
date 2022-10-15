module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;



    opens com.example.dictionary to javafx.fxml;
    exports com.example.dictionary;
    exports com.example.dictionary.base;
    opens com.example.dictionary.base to javafx.fxml;
    exports com.example.dictionary.controller;
    opens com.example.dictionary.controller to javafx.fxml;
}