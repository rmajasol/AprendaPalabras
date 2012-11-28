/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class UserNotFoundException extends Exception{

    public UserNotFoundException(String string) {
        super(string);
    }
}
