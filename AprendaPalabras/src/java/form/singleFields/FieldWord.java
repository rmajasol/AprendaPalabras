/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import error.ErrorMsgs;
import error.Errors;
import exceptions.WrongFieldException;
import form.SingleField;
import form.interfaces.IFieldCheck;
import javax.servlet.http.HttpServletRequest;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class FieldWord extends SingleField implements IFieldCheck {

    public FieldWord(HttpServletRequest hsr, String fieldName) {
        this.fieldName = fieldName;
        fieldValue = getFieldValue_firstUpp(hsr.getParameter(fieldName),
                Pmsgs.WORD_FROM_PMSG);
    }

    public FieldWord(HttpServletRequest hsr, String fieldName, String promptMsg) {
        this.fieldName = fieldName;
        fieldValue = getFieldValue_firstUpp(hsr.getParameter(fieldName), promptMsg);
    }
    
    public FieldWord(String fieldName, String fieldValue){
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Comprueba la palabra a buscar y si hay error se añade al validador
     * @param validator 
     */
    public void check(HttpServletRequest hsr, Errors errors) {
        try {
            check_empty(fieldValue);
            if (fieldValue.length() > 140) {
                throw new WrongFieldException(ErrorMsgs.OVERSIZED_WORD);
            }
            if (fieldValue.contains("  ")) {
                throw new WrongFieldException(ErrorMsgs.DOUBLE_WHITESPACED);
            }
            keepValue(hsr);
        } catch (WrongFieldException ex) {
            errors.putWrongField(fieldName, ex.getMessage());
        }
    }

    public boolean isEmpty() {
        return fieldValue.isEmpty();
    }
}
