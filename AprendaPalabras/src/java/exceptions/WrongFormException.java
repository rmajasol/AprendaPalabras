/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class WrongFormException extends Exception {
    
    String wrongFieldName;
    
    public WrongFormException() {
    }

    public WrongFormException(String string) {
        super(string);
    }

    public WrongFormException(String wrongFieldName, String msg) {
        super(msg);
        this.wrongFieldName = wrongFieldName;
    }

    public String getWrongFieldName() {
        return wrongFieldName;
    }
}
