/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.interfaces;

import error.Validator;

/**
 *
 * @author yomac
 */
public interface IFieldGroupCheck {
    
    /**
     * Chequea parte de los campos de un formulario. Por ejemplo para elegir
     * un lenguaje comprueba tanto el selector como el campo de texto.
     * @param v 
     */
    void check(Validator v);
}
