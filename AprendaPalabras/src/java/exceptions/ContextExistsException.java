/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class ContextExistsException extends Exception {

    public ContextExistsException() {
        super("La frase ejemplo escrita ya existe");
    }
}
