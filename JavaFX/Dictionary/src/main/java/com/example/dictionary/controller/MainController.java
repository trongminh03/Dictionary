package com.example.dictionary.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button apiTransButton;

    @FXML
    private Button bookmarkButton;
    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private BorderPane mainPane;

    SearchController searchController;

    @FXML
    private AnchorPane searchPane;


    private void setMainContent(AnchorPane anchorPane) {
        mainContent.getChildren().setAll(anchorPane);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
            searchPane = loader.load();
            searchController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPane.setCenter(searchPane);
    }
}