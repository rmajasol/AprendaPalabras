/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import error.ErrorMsgs;
import error.Validator;

/**
 *
 * @author yomac
 */
public class WordNotFoundException extends Exception{

    public WordNotFoundException(String string) {
        super(string);
    }
}
