/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import error.ErrorMsgs;
import error.Errors;
import exceptions.LoginIncorrectException;
import exceptions.WrongFieldException;
import form.SingleField;
import form.interfaces.IFieldCheck;
import form.interfaces.IFieldCheckToLogin;
import javax.servlet.http.HttpServletRequest;

/**
 * Comprueba si la contraseña se introdujo correctamente.
 * @author yomac
 */
public class FieldPassword extends SingleField
        implements IFieldCheck, IFieldCheckToLogin {

    public FieldPassword(HttpServletRequest hsr) {
        fieldName = "password";
        fieldValue = hsr.getParameter("password");
    }

    public void checkSome() throws WrongFieldException {
        check_EWA(fieldValue);
        if (fieldValue.length() > 20) {
            throw new WrongFieldException(ErrorMsgs.OVERSIZED_PASSWORD);
        }
        if (fieldValue.length() < 6) {
            throw new WrongFieldException(ErrorMsgs.UNDERSIZED_PASSWORD);
        }
    }
    
    public void check(HttpServletRequest hsr, Errors errors) {
        try {
            checkSome();
            hsr.setAttribute(fieldName, fieldValue);
        } catch (WrongFieldException wfE) {
            errors.putWrongField(fieldName, wfE.getMessage());
        }
        //si está correcto se mete en la colección de campos correctos
    }

    public void checkToLogin() throws LoginIncorrectException {
        try {
            checkSome();
        } catch (WrongFieldException ex) {
            throw new LoginIncorrectException(ErrorMsgs.LOGIN_INCORRECT);
        }
    }
}