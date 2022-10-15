package com.example.dictionary.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dictionary {
    private static final String SPLITTING_PATTERN = "<html>";
    private String PATH;

    private String BOOKMARK_PATH;
    private ArrayList<Word> words = new ArrayList<Word>();
    private ArrayList<Word> bookmardWords = new ArrayList<Word>();


    public Dictionary(String path, String bookmarkPath) {
        PATH = path;
        BOOKMARK_PATH = bookmarkPath;
        loadDataFromHTMLFile(PATH, words);
        loadDataFromHTMLFile(BOOKMARK_PATH, bookmardWords);
    }

    public Dictionary() {

    }

    public String getPath() {
        return PATH;
    }

    public String getBookmarkPath() {
        return BOOKMARK_PATH;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public ArrayList<Word> getBookmarkWords() {
        return bookmardWords;
    }


    public void sortWords(ArrayList<Word> tmp) {
        Collections.sort(tmp, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getTarget().toLowerCase().compareTo(o2.getTarget().toLowerCase());
            }
        });
    }

    public void loadDataFromHTMLFile(String path, ArrayList<Word> tmp) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SPLITTING_PATTERN);
                String word = parts[0];
                String definition = SPLITTING_PATTERN + parts[1];
                tmp.add(new Word(word, definition));
            }
            sortWords(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void exportWordToHTMLFile(String path, String spelling) {
//        try {
//            File file = new File(path);
//            FileWriter fileWriter = new FileWriter(file, true);
//            int index = Collections.binarySearch(words, new Word(spelling, null));
//            String meaning = words.get(index).getExplain();
//            fileWriter.write(spelling + meaning + "\n");
//            fileWriter.flush();
//            fileWriter.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void addWordToFile(String spelling, String meaning, String path) {
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(spelling + meaning + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateToFile(String path) {
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            for (Word word : words) {
                fileWriter.write(word.getTarget() + word.getExplain() + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWord(Word word) {
        words.add(word);
        sortWords(words);
        updateToFile(PATH);
    }


    public void removeWord(int index) {
        words.remove(index);
        updateToFile(PATH);
    }

    public void removeWord(String target, ArrayList<Word> tmp) {
        int index = binarySearchWord(0, tmp.size(), target, tmp);
        if (index == -1) return;
        words.remove(index);
        updateToFile(PATH);
    }

    public int binarySearchWord(int start, int end, String target, ArrayList<Word> tmp) {
        if (end < start) return -1;

        int mid = start + (end - start) / 2;
        if (target.equals(tmp.get(mid).getTarget())) return mid;
        else if (target.compareTo(tmp.get(mid).getTarget()) > 0) {
            return binarySearchWord(mid + 1, end, target, tmp);
        }
        else if (target.compareTo(tmp.get(mid).getTarget()) < 0) {
            return binarySearchWord(start, mid - 1, target, tmp);
        }

        return -1;
    }




}
