package Dictionary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException; 

public class DictionaryController implements ActionListener {

    private DictionaryApplication app; 

    public DictionaryController(DictionaryApplication app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String src = e.getActionCommand(); 

        if (src.equals("Add Word")) {
            this.app.addWord(); 
        }

        if (src.equals("Remove Word")) {
            this.app.removeWord();
        }

        if (src.equals("Update Word")) {
            this.app.updateWord();
        }
        
        if (src.equals("Quick Translate")) {
            try {
                this.app.APItranslate();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    
}
