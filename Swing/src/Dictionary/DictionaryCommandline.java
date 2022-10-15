package Dictionary;

public class DictionaryCommandline extends DictionaryManagement {
    
    public DictionaryCommandline(Dictionary dict) {
        super(dict);
    }

    public void showAllWords() {
        System.out.printf("%-4s|%-20s|%s\n",
                         "No", "English", "Vietnamese"); 
        
        Dictionary dict = super.dict; 
        for (int i = 0; i < dict.getWords().size(); i++) {
            String englishWord = dict.getWords().get(i).getTarget(); 
            String meaning = dict.getWords().get(i).getExplain(); 
            System.out.printf("%-4d|%-20s|%s\n",
                         i + 1, englishWord, meaning);
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords(); 
    }

    public void dictionaryAdvanced() {
        insertFromFile(); 
        showAllWords();
        dictionaryLookup();
        addWord(); 
        update(); 
        removeWord();
        dictionarySearcher();
        dictionaryExportToFile();
        showAllWords();
    }

    public void dictionary() {
        insertFromFile();
        while (true) {
            showMenu();
            String answer = sc.nextLine(); 
            switch (answer) {
                case "1":
                    dictionaryLookup();
                    break;
                case "2": 
                    addWord();
                    break; 
                case "3": 
                    update(); 
                    break; 
                case "4": 
                    removeWord();
                    break; 
                case "5": 
                    dictionarySearcher();
                    break; 
                case "6": 
                    dictionaryExportToFile();
                    break; 
                case "7": 
                    showAllWords();
                    break; 
                case "8": 
                    sc.close(); 
                    return; 
                default:
                    break;
            }
        }
    }
}
