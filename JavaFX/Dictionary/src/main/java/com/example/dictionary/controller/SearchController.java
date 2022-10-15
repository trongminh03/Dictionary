package com.example.dictionary.controller;

import com.example.dictionary.base.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class SearchController extends GeneralController implements Initializable {

    private final ArrayList<Word> searchWordTemp = new ArrayList<Word>();

//    @FXML
//    private ListView<String> list;

//    @FXML
//    private TextField tfSearch;

//    @FXML
//    private WebView definitionView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Word temp : dict.getWords()) {
            searchList.add(temp.getTarget());
        }
        wordListView.setItems(searchList);

        // khi vừa mở từ điển lên thì có thể ẩn vào từ tra ngay, terminal k hiện lỗi index out of bounds
        searchWordTemp.addAll(dict.getWords());
    }

    public void setSearchListView() {
        searchList.clear();
        if (tfSearch.getText().equals("")) {
            searchWordTemp.clear();
            searchWordTemp.addAll(dict.getWords());
        }

        for (Word tmp : searchWordTemp) {
            searchList.add(tmp.getTarget());
        }
        wordListView.setItems(searchList);

    }

    @FXML
    public void searchAction() {
        searchWordTemp.clear();
        searchList.clear();
        String target = tfSearch.getText().trim().toLowerCase();
        updateListView(target, searchWordTemp);
        setSearchListView();
        try {
            wordListView.getSelectionModel().select(0);
            String meaning = searchWordTemp.get(0).getExplain();
            definitionView.getEngine().loadContent(meaning);
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void showDefinition() {
        String spelling = wordListView.getSelectionModel().getSelectedItem();
        tfSearch.setText(spelling);
        if (spelling == null) return;

        int index = dict.binarySearchWord(0, searchWordTemp.size(), spelling, searchWordTemp);
        String meaning = searchWordTemp.get(index).getExplain();
        definitionView.getEngine().loadContent(meaning);
    }

    @FXML
    public void handleClickDeleteButton() {
        super.handleClickDeleteButton();
        searchList.clear();
        wordListView.getItems().clear();
        for (Word tmp : dict.getWords()) {
            searchList.add(tmp.getTarget());
        }
        wordListView.setItems(searchList);
        searchWordTemp.addAll(dict.getWords());
    }


}
