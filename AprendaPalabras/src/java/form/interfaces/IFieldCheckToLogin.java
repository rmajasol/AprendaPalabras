/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.interfaces;

import exceptions.LoginIncorrectException;

/**
 *
 * @author yomac
 */
public interface IFieldCheckToLogin {
    
    /**
     * No especificamos el error para cada campo, sino que directamente devuelve el
     * el mensaje de "datos de acceso incorrectos" en caso de lanzar la excepción.
     * @param errors
     * @throws LoginIncorrectException 
     */
    public void checkToLogin() throws LoginIncorrectException;
}
