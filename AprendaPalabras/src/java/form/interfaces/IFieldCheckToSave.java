/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.interfaces;

import error.Errors;
import error.Validator;
import exceptions.WrongFieldException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public interface IFieldCheckToSave {

    /**
     * Además de comprobar que el campo está bien escrito mira si no hay un registro
     * con el mismo nombre. En caso contrario se lanza una excepción 
     * {@link WrongFieldException} que se captura en el mismo cuerpo del método,
     * donde se añade al validador el campo como incorrecto:
     * {@code validator.putWrongField(fieldName, wfE.getMessage());}
     * @param validator 
     */
    public void checkToSave(HttpServletRequest hsr, Errors errors);
}
