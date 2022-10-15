package Dictionary;
public class Engine {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        // DictionaryCommandline dictCL = new DictionaryCommandline(dict);
        // dictCL.insertFromFile();
        DictionaryApplication dictApp = new DictionaryApplication(dict);
        dictApp.init();
    }
}