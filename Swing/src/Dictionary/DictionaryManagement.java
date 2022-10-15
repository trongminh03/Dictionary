package Dictionary;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DictionaryManagement {
    Dictionary dict; 
    Scanner sc = new Scanner(System.in);

    public DictionaryManagement() {
        
    }

    public DictionaryManagement(Dictionary dict) {
        this.dict = dict;
    }

    public void insertFromCommandline() { 
        System.out.println("Number of words: "); 
        int numberOfWords = sc.nextInt(); 
        sc.nextLine(); 
        for (int i = 0; i < numberOfWords; i++) {
            System.out.println("Enter the english word: ");
            String englishWord = sc.nextLine(); 
            System.out.println("Enter its meaning: ");
            String meaning = sc.nextLine(); 
            dict.addWord(new Word(englishWord, meaning));
        }
    }

    public void insertFromFile() {
        File file = new File("src/message.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String vn, en, mid = "";
                StringTokenizer stk = new StringTokenizer(line, "(|)|/");
                en = stk.nextToken().trim();
                stk = new StringTokenizer(line, "/");
                do {
                    vn = stk.nextToken().trim();
                } while (stk.hasMoreTokens());
                mid = line.substring(en.length(), line.length() - vn.length()).trim();
                String ret = "";
                String []sp = mid.split("\\s+");

                for (String o : sp){
                    ret = ret + o.trim() + " ";
                }
                vn = ret + vn;
                dict.addWord(new Word(en, vn));
            }
            sc.close(); 

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    public void dictionaryLookup() {
        System.out.println("LOOKUP: ");
        System.out.println("Enter the word: ");
        String word = sc.nextLine(); 
        int index = contains(word); 
        if (index != -1) {
            System.out.println("Vietnamese meaning: "
                                + dict.getWords().get(index).getExplain()); 
        }
        else System.out.println("The word does not exist!");
    }

    public void removeWord() {
        System.out.println("REMOVE WORD: ");
        System.out.println("Enter word u wanna erase: "); 
        String word = sc.nextLine(); 
        int index = contains(word); 
        if (index != -1) {
            dict.removeWord(index);
        }
        else System.out.println("Word does not exist!"); 
    }

    public void update() {
        System.out.println("UPDATE: ");
        System.out.print("Enter the word u wanna update: "); 
        String word = sc.nextLine(); 
        int index = contains(word); 
        if (index != 1) {
            System.out.print("Enter new meaning of the word: ");
            String meaning = sc.nextLine(); 
            dict.getWords().get(index).setExplain(meaning);
        }
        else {
            System.out.println("Word does not exist, do u wanna add new word ?");
            String answer = sc.nextLine(); 
            answer.toLowerCase(); 
            if (answer.equals('y') || answer.equals("yes")) {
                System.out.println("Enter meaning of the word: ");
                String meaning = sc.nextLine(); 
                dict.addWord(new Word(word, meaning));
            }
        }
    }

    public void addWord() {
        System.out.println("ADD WORD:");
        System.out.println("Enter the english word: ");
        String englishWord = sc.nextLine(); 
        if (contains(englishWord) != -1) {
            System.out.println("The word is already exist!");
        }
        else {
            System.out.println("Enter its meaning: ");
            String meaning = sc.nextLine(); 
            dict.addWord(new Word(englishWord, meaning));
        }
    }

    public int contains(String word) {
        for (int i = 0; i < dict.getWords().size(); i++) {
            if (word.equals(dict.getWords().get(i).getTarget())) {
                return i; 
            }
        }
        return -1; 
    }

    public void dictionarySearcher() {
        System.out.println("SEARCHER: ");
        System.out.println("Enter word to search: ");
        String word = sc.nextLine(); 
        boolean searcher = false; 
        for (int i = 0; i < dict.getWords().size(); i++) {
            if (dict.getWords().get(i).getTarget().startsWith(word)) {
                if (!searcher) {
                    System.out.println("You mean: ");
                    searcher = true; 
                } 
                System.out.println(dict.getWords().get(i).getTarget());
            }
        }
        if (!searcher) System.out.println("Word does not exist!");
    }

    public void dictionaryExportToFile() {
        File file = new File("src/exportedFile.txt");

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < dict.getWords().size(); i++) {
                bf.write(dict.getWords().get(i).getTarget()); 
                bf.write(": "); 
                bf.write(dict.getWords().get(i).getExplain()); 
                bf.newLine();
            } 
        } catch (Exception e) {
            System.out.println("Exception!!!");
        }  
    }

    public void showMenu() {
        System.out.println("MENU: "); 
        System.out.println("1. Lookup"); 
        System.out.println("2. Add word"); 
        System.out.println("3. Update"); 
        System.out.println("4. Remove word");  
        System.out.println("5. Searcher"); 
        System.out.println("6. Export to file");
        System.out.println("7. Show all words");
        System.out.println("8. Exit");
    }
}
 