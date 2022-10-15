package com.example.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("controller/main.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("controller/main.fxml")));
        Scene scene = new Scene(root, 900, 675);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}