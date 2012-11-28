/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class TypedLanguageExistsException extends Exception {    
    public TypedLanguageExistsException() {
    }

    public TypedLanguageExistsException(String string) {
        super(string);
    }
}
