/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class LoginIncorrectException extends Exception {

    public LoginIncorrectException(String string) {
        super(string);
    }

    public LoginIncorrectException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public LoginIncorrectException() {
    }
    
    
    
    
}
