package Dictionary;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import Validation.Validation;


public class DictionaryApplication extends JFrame {
    Validation valid = new Validation();
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
    String format = "format:(Part of speech) /pronounce/ meaning"; 
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    final private Font font2 = new Font("MV Boli", Font.BOLD, 30); 
    private JTextField tfInput, tfNotification;
    private JList<String> lList;
    private JTextArea taMeaning;
    private DefaultListModel<String> mod;
    private JScrollPane spScroller;
    private JPanel leftPanel, rightPanel, topPanel, bottomPanel; 
    private JLabel lbTopLabel; 
    private int importantNumber = 24;

    JButton addWord, eraseWord, updateWord; 
    private JTextField tfAddEnglish, tfAddMeaning, tfEraseSpace;
    private JTextArea taAPItransEnglish; 
    private JButton APItrans; 
    private JTextField tfUpdateEnglish, tfUpdateMeaning;  

    private Dictionary dict;

    public DictionaryApplication() {

    }

    public DictionaryApplication(Dictionary dict) {
        this.dict = dict;
    }

    public void init() {
        insertFromFile();
        
        tfInput = new JTextField();
        ActionListener ac = new DictionaryController(this); 
        lList = new JList<String>();
        mod = new DefaultListModel<>();
        taMeaning = new JTextArea();
        spScroller = new JScrollPane
        (   lList, 
            JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        topPanel = new JPanel(); 
        lbTopLabel = new JLabel(); 
        bottomPanel = new JPanel();
        addWord = new JButton("Add Word"); 
        eraseWord = new JButton("Remove Word"); 
        updateWord = new JButton("Update Word"); 
        tfAddEnglish = new JHintTextField("word to add");  
        tfAddMeaning = new JHintTextField(format); 
        tfUpdateEnglish = new JHintTextField("word to update"); 
        tfUpdateMeaning = new JHintTextField(format); 
        tfEraseSpace = new JHintTextField("Erase word");
        tfNotification = new JTextField(); 
        taAPItransEnglish = new JTextArea(); 
        APItrans = new JButton("Quick Translate");
        

        
        tfInput.setFont(mainFont);
        tfInput.setForeground(Color.RED); 
        
        
        taMeaning.setFont(mainFont);
        taMeaning.setEditable(false);
        taMeaning.setLineWrap(true);
        tfNotification.setFont(font2); 
        tfNotification.setToolTipText("Notifications");
        tfNotification.setEditable(false);
        tfNotification.setBackground(Color.white);

        tfInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                tfNotification.setText(""); 
                taAPItransEnglish.setText("");
                String text = "– " + tfInput.getText().trim();
                text += e.getKeyChar();
                text = text.trim().toLowerCase();
                for (int i = 0; i < mod.size(); i++) {
                    String val = mod.getElementAt(i);
                    val = val.toLowerCase();
                    if (val.startsWith(text)) {
                        lList.setSelectedIndex(i);
                        JScrollBar sb = spScroller.getVerticalScrollBar();
                        sb.setValue(i * importantNumber);
                        return;
                    }
                }
                taMeaning.setText("This word not found");
                lList.clearSelection();
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        lList.setFont(mainFont);
        for (int i = 0; i < dict.getWords().size(); i++) {
            mod.addElement(dict.getWords().get(i).getTarget());
        }
        lList.setModel(mod);
        lList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!lList.isSelectionEmpty()) {
                    int index = lList.getSelectedIndex();
                    String meaning = dict.getWords().get(index).getExplain();
                    taMeaning.setText(meaning);
                }
            }
        });
        
        
        leftPanel.setLayout(new BorderLayout(10, 10));
        leftPanel.add(tfInput, BorderLayout.NORTH);
        leftPanel.add(spScroller, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(200, 300));
        
        rightPanel.setLayout(new BorderLayout(0, 10));
        rightPanel.add(taMeaning, BorderLayout.CENTER);
        rightPanel.add(tfNotification, BorderLayout.SOUTH); 
        rightPanel.setPreferredSize(new Dimension(300, 300));
        
        topPanel.setPreferredSize(new Dimension(800, 30));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.cyan);
        lbTopLabel.setText("Dictionary");
        lbTopLabel.setVerticalAlignment(SwingConstants.CENTER);
        lbTopLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lbTopLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
        topPanel.add(lbTopLabel, BorderLayout.CENTER);
        
        bottomPanel.setLayout(new FlowLayout(SwingConstants.LEADING, 10, 10));
        bottomPanel.setPreferredSize(new Dimension(800, 280)); 
        
        addWord.setFont(new Font("MV Boli", Font.BOLD, 20)); 
        eraseWord.setFont(new Font("MV Boli", Font.BOLD, 20));
        updateWord.setFont(new Font("MV Boli", Font.BOLD, 20)); 
        APItrans.setFont(new Font("MV Boli", Font.BOLD, 20)); 
        
        addWord.addActionListener(ac); 
        eraseWord.addActionListener(ac); 
        updateWord.addActionListener(ac);
        APItrans.addActionListener(ac);
        
        // tfAddEnglish.setFont(new Font("Arial", Font.BOLD, 25));
        // tfAddMeaning.setFont(new Font("Arial", Font.BOLD, 25));
        
        tfAddEnglish.setPreferredSize(new Dimension(200, 30)); 
        tfAddMeaning.setPreferredSize(new Dimension(400, 30)); 
        // tfUpdateEnglish.setFont(new Font("Arial", Font.BOLD, 25));
        // tfUpdateMeaning.setFont(new Font("Arial", Font.BOLD, 25)); 
        tfUpdateEnglish.setPreferredSize(new Dimension(200, 30)); 
        tfUpdateMeaning.setPreferredSize(new Dimension(360, 30)); 
        // tfEraseSpace.setFont(new Font("Arial", Font.BOLD, 25)); 
        tfEraseSpace.setPreferredSize(new Dimension(500, 30));
        taAPItransEnglish.setPreferredSize(new Dimension(500, 100)); 
        taAPItransEnglish.setFont(new Font("MV Boli", Font.PLAIN, 20)); 
        taAPItransEnglish.setLineWrap(true);
        
        bottomPanel.add(addWord);
        bottomPanel.add(tfAddEnglish); 
        bottomPanel.add(tfAddMeaning);  
        bottomPanel.add(updateWord); 
        bottomPanel.add(tfUpdateEnglish); 
        bottomPanel.add(tfUpdateMeaning); 
        bottomPanel.add(eraseWord); 
        bottomPanel.add(tfEraseSpace); 
        bottomPanel.add(APItrans); 
        bottomPanel.add(taAPItransEnglish); 
        
        setLayout(new BorderLayout(20, 5));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH); 
        add(bottomPanel, BorderLayout.SOUTH); 
        setTitle("Dictionary");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addWord() {
        String englishWord = "– " + tfAddEnglish.getText().trim().toLowerCase(); 
        int index = dict.binarySearchWord(0, dict.getWords().size(), englishWord); 
        System.out.println(index);
        if (index != -1) {
            tfNotification.setText("Word is already in dictionary"); 
            tfNotification.setForeground(Color.red);
            tfAddEnglish.setText(""); 
            tfAddEnglish.addFocusListener(new JHintTextField("Word to add"));
            tfAddMeaning.setText(""); 
            System.out.println("Word is already in dictionary");
        }
        else {
            String meaning = tfAddMeaning.getText();
            if (!valid.checkValidString("\\(\\D+\\)\\s/\\D+/\\s.*", meaning)) {
                // format: (loai tu) /phat am/ nghia
                // ((JHintTextField)tfAddEnglish).setButtonPress(true);  
                tfAddEnglish.setText(""); 
                tfAddMeaning.setText("");
                tfNotification.setText("Invalid input, please follow the format"); 
                tfNotification.setForeground(Color.red); 
            }
            else {
                this.dict.addWord(new Word(englishWord, meaning));
                mod.add(dict.getIndex(englishWord), englishWord); 
                dictionaryExportToFile();        
                tfNotification.setText("Add word successfully"); 
                tfNotification.setForeground(Color.cyan);
                tfAddEnglish.setText(""); 
                tfAddMeaning.setText(""); 
                System.out.println("Add word successfully");
            }
        }
    }

    public void removeWord() {
        String englishWord = "– " + tfEraseSpace.getText().trim().toLowerCase(); 
        int index = dict.getIndex(englishWord); 
        if (index == -1) {
            tfNotification.setText("Word is not found");
            tfNotification.setForeground(Color.red);
            System.out.println("Word not found");
        }
        else {
            tfNotification.setText("Erase word successfully"); 
            tfNotification.setForeground(Color.cyan);
            this.dict.removeWord(index);
            mod.remove(index); 
            System.out.println("Erase word successfully");
            tfEraseSpace.setText(""); 
            dictionaryExportToFile(); 
        }
    }

    public void updateWord() {
        String englishWord = "– " + tfUpdateEnglish.getText().trim().toLowerCase(); 
        String newMeaning = tfUpdateMeaning.getText().trim().toLowerCase();
        int index = dict.getIndex(englishWord); 
        if (index == -1) {
            tfNotification.setText("Word is not found, please add a new word");
            tfNotification.setForeground(Color.red);
            System.out.println("Word is not found, please add a new word");
        } 
        else if (!valid.checkValidString("\\(\\D+\\)\\s/\\D+/\\s.*", newMeaning)) {
            tfNotification.setText("Invalid input, please follow the format"); 
            tfNotification.setForeground(Color.red); 
        }
        else {
            tfNotification.setText("Update word successfully"); 
            tfNotification.setForeground(Color.cyan);
            dict.updateWord(new Word(englishWord, newMeaning));
            System.out.println("Update word successfully");
            tfUpdateEnglish.setText(""); 
            tfUpdateMeaning.setText("");
            dictionaryExportToFile(); 
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
                String[] sp = mid.split("\\s+");

                for (String o : sp) {
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

    public void dictionaryExportToFile() {
        File file = new File("src/message.txt");

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < dict.getWords().size(); i++) {
                bf.write(dict.getWords().get(i).getTarget()); 
                bf.write(" "); 
                bf.write(dict.getWords().get(i).getExplain()); 
                bf.newLine();
            } 
        } catch (Exception e) {
            System.out.println("Exception!!!");
        }  
    }

    public String translate(String fromLang, String toLang, String text) throws IOException {
        String jsonPayload = new StringBuilder()
        .append("{")
        .append("\"fromLang\":\"")
        .append(fromLang)
        .append("\",")
        .append("\"toLang\":\"")
        .append(toLang)
        .append("\",")
        .append("\"text\":\"")
        .append(text)
        .append("\"")
        .append("}")
        .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
            (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
            ));
        StringBuilder output = new StringBuilder();
        String inputLine; 
        while ((inputLine = br.readLine()) != null) {
            output.append(inputLine); 
        }
        conn.disconnect();
        return output.toString(); 
    }
 
    public void APItranslate() throws IOException {
        String englishWord = taAPItransEnglish.getText(); 
        String meaning = translate("en", "vi", englishWord);
        taMeaning.setText(meaning); 
        tfNotification.setText("API translate successfully"); 
        tfNotification.setForeground(Color.cyan);
        System.out.println("Quick translate successfully");
    }


    public void runApplication() {
        
    }
}
    
