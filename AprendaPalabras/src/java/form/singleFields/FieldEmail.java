/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import data.dao.UserDAO;
import error.ErrorMsgs;
import error.Errors;
import exceptions.WrongFieldException;
import form.SingleField;
import form.interfaces.IFieldCheckSome;
import form.interfaces.IFieldCheckToRead;
import form.interfaces.IFieldCheckToSave;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author yomac
 */
public class FieldEmail extends SingleField
        implements IFieldCheckToRead, IFieldCheckToSave, IFieldCheckSome {

    public FieldEmail(HttpServletRequest hsr) {
        fieldName = "email";
        fieldValue = hsr.getParameter("email");
    }

    public void checkSome() throws WrongFieldException {
        check_empty(fieldValue);
        check_whitespaces(fieldValue);
        if (fieldValue.length() > 254) {
            throw new WrongFieldException(ErrorMsgs.OVERSIZED_EMAIL);
        }
        EmailValidator ev = EmailValidator.getInstance();
        if (!ev.isValid(fieldValue)) {
            throw new WrongFieldException(ErrorMsgs.INCORRECT_SINTAX_EMAIL);
        }
    }

    public void checkToSave(HttpServletRequest hsr, Errors errors) {
        try {
            checkSome();
            if (new UserDAO().exists(fieldName, fieldValue)) {
                throw new WrongFieldException(ErrorMsgs.EMAIL_EXISTS);
            }
            hsr.setAttribute(fieldName, fieldValue);
        } catch (WrongFieldException wfE) {
            errors.putWrongField(fieldName, wfE.getMessage());
        }
    }

    public void checkToRead(HttpServletRequest hsr, Errors errors) {
        try {
            checkSome();
            if (!new UserDAO().exists(fieldName, fieldValue)) {
                throw new WrongFieldException(ErrorMsgs.EMAIL_DOESNT_EXISTS);
            }
            hsr.setAttribute(fieldName, fieldValue);
        } catch (WrongFieldException wfE) {
            errors.putWrongField(fieldName, wfE.getMessage());
        }
    }
}