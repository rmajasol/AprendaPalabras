/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Esta excepción es lanzada cada vez que hay algún problema con el envío del email.
 * @author yomac
 */
public class MailNotSentException extends Exception{

    public MailNotSentException(String string) {
        super(string);
    }
}
