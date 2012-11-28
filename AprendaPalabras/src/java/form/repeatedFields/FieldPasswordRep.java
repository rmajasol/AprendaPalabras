/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.repeatedFields;

import form.RepeatedField;
import error.ErrorMsgs;
import error.Errors;
import exceptions.LoginIncorrectException;
import exceptions.WrongFieldException;
import form.interfaces.IFieldCheck;
import form.interfaces.IFieldCheckSome;
import form.interfaces.IFieldCheckToLogin;
import javax.servlet.http.HttpServletRequest;
import utils.words.Pmsgs;

/**
 * Comprueba si la contraseña se introdujo correctamente.
 * @author yomac
 */
public class FieldPasswordRep extends RepeatedField 
implements IFieldCheckSome, IFieldCheck, IFieldCheckToLogin{
    
    public FieldPasswordRep(HttpServletRequest hsr) {
        fieldName = "password";
        fieldName2 = "password2";
        fieldValue = hsr.getParameter("password");
        fieldValue2 = getFieldValue(hsr.getParameter(fieldName2), 
                Pmsgs.PASSWORD_PMSG);
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
            checkRepeated();
            checkSome();
            keepValue(hsr);
        } catch (WrongFieldException ex) {
            errors.putWrongField(fieldName, ex.getMessage());
        }
    }

    public void checkToLogin() throws LoginIncorrectException {
        try {
            checkSome();
        } catch (WrongFieldException ex) {
            throw new LoginIncorrectException(ErrorMsgs.LOGIN_INCORRECT);
        }
    }
}