/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.interfaces;

import error.Errors;
import exceptions.WrongFieldException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public interface IFieldCheckToRead {

    /**
     * Se comprueba que exista el valor para el campo, si no se lanza una excepción 
     * {@link WrongFieldException} que se captura en el mismo cuerpo del método,
     * donde se añade a la clase errors el campo incorrecto
     * @param validator 
     */
    public void checkToRead(HttpServletRequest hsr, Errors errors);
}
