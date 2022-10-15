package Dictionary;

import javax.swing.JTextField;
import java.awt.event.*; 
import java.awt.*; 

public class JHintTextField extends JTextField implements FocusListener {

    private final String hint; 
    private boolean showingHint; 
    private Font lostFont = new Font("Arial", Font.ITALIC, 15); 
    private Font gainFont = new Font("Arial", Font.BOLD, 25); 

    public JHintTextField() {
        this(null);
    }

    public JHintTextField(final String hint) {
        super(hint); 
        this.hint = hint; 
        this.showingHint = true; 
        this.setForeground(Color.gray);    
        setFont(lostFont);  
        super.addFocusListener(this); 
    }

    public JHintTextField(final String hint, Font lostFont, Font gainFont) {
        super(hint); 
        this.hint = hint; 
        this.showingHint = true; 
        this.lostFont = lostFont; 
        this.gainFont = gainFont; 
        setFont(lostFont);
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(getText().isEmpty()) {
          super.setText("");
          showingHint = false;
          setFont(gainFont);
          setForeground(Color.black);
        } 
        else {
            setText(getText());
            setFont(gainFont);  
            setForeground(Color.black);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty() || getText().equals(hint)) {
            super.setText(hint);
            setFont(lostFont); 
            setForeground(Color.gray);
            showingHint = true;
        }
        else {
            setText(getText()); 
            setFont(gainFont); 
            setForeground(Color.black);
        }
    }   

    @Override
    public String getText() {
        // return showingHint ? "" : super.getText();
        if (showingHint) return ""; 
        else return super.getText();
    }
    
}
