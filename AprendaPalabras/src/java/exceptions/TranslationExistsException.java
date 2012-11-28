/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class TranslationExistsException extends Exception {

    public TranslationExistsException(String wFrom, String lFrom,
            String lTo, String wTo) {
        super("La traducción " + wFrom + " (" + lFrom + ") -> "
                + wTo + " (" + lTo + ") ya existe");
    }
}
