/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import error.ErrorMsgs;

/**
 *
 * @author yomac
 */
public class TranslationNotFoundException extends Exception{

    public TranslationNotFoundException() {
        super(ErrorMsgs.TRANSLATION_NOT_FOUND);
    }
}
