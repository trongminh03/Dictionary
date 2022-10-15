package com.example.dictionary.controller;

import com.example.dictionary.base.Dictionary;
import com.example.dictionary.base.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

import java.util.ArrayList;

public class GeneralController extends MainController implements Initializable {
    private static final String PATH = "src/main/vocab/eng_vie.txt";
    private static final String BOOKMARK_PATH = "src/main/vocab/bookmark.txt";

    @FXML
    protected ListView<String> wordListView;

//    protected ObservableList<String> bookmarkSearch = FXCollections.observableArrayList();
    protected  ObservableList<String> searchList = FXCollections.observableArrayList();

    protected static Dictionary dict = new Dictionary(PATH, BOOKMARK_PATH);


    // SearchController
    @FXML
    protected TextField tfSearch;

    @FXML
    protected WebView definitionView;

    public void updateListView(String target, ArrayList<Word> res) {
        for (Word word : dict.getWords()) {
            if (word.getTarget().startsWith(target)) {
                res.add(word);
            }
        }
    }


    @FXML
    public void handleClickDeleteButton() {
        String target = tfSearch.getText();
        if (target.equals("")) {
            System.out.println("alert");
            return;
        }

        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this word ?", yes, no);
        alert.setTitle("ALERT");
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == yes) {
            dict.removeWord(target, dict.getWords());
            dict.removeWord(target, dict.getBookmarkWords());
            definitionView.getEngine().loadContent("");
            tfSearch.setText("");
        }
    }


}
