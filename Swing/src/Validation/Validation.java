package Validation;

public class Validation {
    public boolean checkValidString(String format, String text) {
        try {
            if (text.matches(format)) {
                return true; 
            }
        } catch (Exception e) {
            return false; 
        }
        return false; 
    }
}
