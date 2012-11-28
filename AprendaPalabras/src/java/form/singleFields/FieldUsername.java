/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import error.ErrorMsgs;
import exceptions.WrongFieldException;
import data.dao.UserDAO;
import error.Errors;
import exceptions.LoginIncorrectException;
import form.SingleField;
import form.interfaces.IFieldCheckSome;
import form.interfaces.IFieldCheckToLogin;
import form.interfaces.IFieldCheckToRead;
import form.interfaces.IFieldCheckToSave;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public class FieldUsername extends SingleField implements
        IFieldCheckSome, IFieldCheckToLogin, IFieldCheckToRead,
        IFieldCheckToSave {

    public FieldUsername(HttpServletRequest hsr) {
        fieldName = "username";
        fieldValue = hsr.getParameter("username");
    }

    public void checkSome() throws WrongFieldException {
        check_EWA(fieldValue);
        if (fieldValue.length() > 20) {
            throw new WrongFieldException(ErrorMsgs.OVERSIZED_USERNAME);
        }
        if (fieldValue.length() < 4) {
            throw new WrongFieldException(ErrorMsgs.UNDERSIZED_USERNAME);
        }
    }

    public void checkToSave(HttpServletRequest hsr, Errors errors) {
        try {
            checkSome();
            if (new UserDAO().exists(fieldName, fieldValue)) {
                throw new WrongFieldException(ErrorMsgs.USERNAME_EXISTS);
            }
            keepValue(hsr);
        } catch (WrongFieldException wfE) {
            errors.putWrongField(fieldName, wfE.getMessage());
        }
    }

    public void checkToRead(HttpServletRequest hsr, Errors errors) {
        try {
            checkSome();
            if (!new UserDAO().exists(fieldName, fieldValue)) {
                throw new WrongFieldException(ErrorMsgs.USERNAME_DOESNT_EXISTS);
            }
            keepValue(hsr);
        } catch (WrongFieldException wfE) {
            errors.putWrongField(fieldName, wfE.getMessage());
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