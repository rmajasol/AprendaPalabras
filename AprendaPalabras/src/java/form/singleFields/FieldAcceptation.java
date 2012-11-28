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
import utils.Msgs;

/**
 *
 * @author yomac
 */
public class FieldAcceptation extends SingleField implements IFieldCheck {

    public FieldAcceptation(HttpServletRequest hsr, String pmsgName, String pmsgValue) {
        this.fieldName = "acceptation";
        fieldValue = getFieldValue_firstUpp(hsr, hsr.getParameter(fieldName),
                pmsgName, pmsgValue);
    }

    public FieldAcceptation(HttpServletRequest hsr, String fieldName,
            String pmsgName, String pmsgValue) {
        this.fieldName = fieldName;
        fieldValue = getFieldValue_firstUpp(hsr, hsr.getParameter(fieldName),
                pmsgName, pmsgValue);
    }
    
    public FieldAcceptation(String fieldName, String fieldValue){
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Comprueba la palabra a buscar y si hay error se añade al validador
     * @param validator 
     */
    public void check(HttpServletRequest hsr, Errors errors) {
        try {
            if (fieldValue.length() > 100) {
                throw new WrongFieldException(ErrorMsgs.OVERSIZED_WORD);
            }
            if (fieldValue.contains("  ")) {
                throw new WrongFieldException(ErrorMsgs.DOUBLE_WHITESPACED);
            }
            if (!fieldValue.isEmpty()) {
                keepValue(hsr);
            }
        } catch (WrongFieldException ex) {
            errors.putWrongField(fieldName, ex.getMessage());
        }
    }

    public boolean isEmpty() {
        return fieldValue.isEmpty();
    }
}
