/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.repeatedFields;

import form.RepeatedField;
import data.dao.UserDAO;
import error.ErrorMsgs;
import error.Errors;
import exceptions.WrongFieldException;
import form.interfaces.IFieldCheckSome;
import form.interfaces.IFieldCheckToSave;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.routines.EmailValidator;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class FieldEmailRep extends RepeatedField
        implements IFieldCheckSome, IFieldCheckToSave {
    
    public FieldEmailRep(HttpServletRequest hsr) {
        fieldName = "email";
        fieldValue = hsr.getParameter("email");
        fieldName2 = "email2";
        fieldValue2 = getFieldValue(hsr.getParameter(fieldName2), 
                Pmsgs.EMAIL_PMSG);
    }

    public void checkSome() throws WrongFieldException {
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
            checkRepeated();
            checkSome();
            if (new UserDAO().exists(fieldName, fieldValue)) {
                throw new WrongFieldException(ErrorMsgs.EMAIL_EXISTS);
            }
            keepValue(hsr);
        } catch (WrongFieldException wfE) {
            errors.putWrongField(fieldName, wfE.getMessage());
        }
    }
}