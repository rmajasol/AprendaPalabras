/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import error.ErrorMsgs;
import exceptions.WrongFieldException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public class RepeatedField extends Form {

    protected String fieldName = null, fieldName2 = null;
    protected String fieldValue = null, fieldValue2 = null;

    /*
     * GETTERS AND SETTERS
     */
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName2() {
        return fieldName2;
    }

    public void setFieldName2(String fieldName2) {
        this.fieldName2 = fieldName2;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue2() {
        return fieldValue2;
    }

    public void setFieldValue2(String fieldValue2) {
        this.fieldValue2 = fieldValue2;
    }

    /**
     * 
     * @param reservedTxt
     * @throws WrongFieldException 
     */
    public void checkRepeated() throws WrongFieldException {
        if (fieldValue.isEmpty() || fieldValue2.isEmpty()) {
            throw new WrongFieldException(ErrorMsgs.FIELDS_NON_REPEATED);
        }
        if (!fieldValue.equalsIgnoreCase(fieldValue2)) {
            throw new WrongFieldException(ErrorMsgs.FIELDS_DOESNT_MATCH);
        }
    }
    
    public void keepValue(HttpServletRequest hsr){
        hsr.setAttribute(fieldName, fieldValue);
        hsr.setAttribute(fieldName2, fieldValue2);
    }
}
