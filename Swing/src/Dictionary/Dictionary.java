package Dictionary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList<Word>();
    
    public Dictionary(ArrayList<Word> words) {
        this.words = words;
    } 

    public Dictionary() {

    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public void addWord(Word word) {
        words.add(word); 
        sortWords();
    }

    public void removeWord(int index) {
        words.remove(index); 
    }

    public void sortWords() {
        Collections.sort(words, new Comparator<Word>() {
            public int compare(Word o1, Word o2) {
                return o1.getTarget().toLowerCase().compareTo(o2.getTarget().toLowerCase()); 
            }
        }); 
    }
    
    public int getIndex(String englishWord) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getTarget().equals(englishWord)) {
                return i; 
            } 
        }
        return -1; 
    }

    public void updateWord(Word word) {
        String englishWord = word.getTarget(); 
        String newMeaning = word.getExplain(); 
        int index = getIndex(englishWord);
        words.get(index).setExplain(newMeaning);
    }

    public int binarySearchWord(int start, int end, String target) {
        if (end < start) return -1;

        int mid = start + (end - start) / 2;
        if (target.equals(words.get(mid).getTarget())) return mid;
        else if (target.compareTo(words.get(mid).getTarget()) > 0) {
            return binarySearchWord(mid + 1, end, target);
        }
        else if (target.compareTo(words.get(mid).getTarget()) < 0) {
            return binarySearchWord(start, mid - 1, target);
        }

        return -1;
    }
} 
