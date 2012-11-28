/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import error.ErrorMsgs;
import exceptions.WrongFieldException;
import javax.servlet.http.HttpServletRequest;
import utils.Common;

/**
 *
 * @author yomac
 */
public class Form {

    public void check_empty(String fieldValue) throws WrongFieldException {
        if (fieldValue.isEmpty()) {
            throw new WrongFieldException(ErrorMsgs.FIELD_EMPTY);
        }
    }

    public void check_whitespaces(String fieldValue) throws WrongFieldException {
        if (fieldValue.contains(" ")) {
            throw new WrongFieldException(ErrorMsgs.FIELD_WHITESPACES);
        }
    }

    public void check_alphanumeric(String fieldValue) throws WrongFieldException {
        if (!fieldValue.matches(Common.ALPHANUMERIC_PATTERN)) {
            throw new WrongFieldException(ErrorMsgs.FIELD_NON_ALPHANUMERIC);
        }
    }

    public void check_EWA(String fieldValue) throws WrongFieldException {
        check_empty(fieldValue);
        check_whitespaces(fieldValue);
        check_alphanumeric(fieldValue);
    }

    /**
     * Si el valor para el campo es igual a la palabra reservada entonces se toma
     * como vacío.
     */
    public String getFieldValue_firstUpp(HttpServletRequest hsr,
            String value, String pmsgName, String pmsgValue) {
        if (value == null || value.equals(pmsgValue) || value.isEmpty()) {
            hsr.setAttribute(pmsgName, pmsgValue);
            return "";
        }
        return Common.firstUpperCase(value);
    }
    
    public String getFieldValue_firstUpp(String value, String pmsgValue) {
        if (value == null || value.equals(pmsgValue) || value.isEmpty()) {
            return "";
        }
        return Common.firstUpperCase(value);
    }

    public String getFieldValue(HttpServletRequest hsr,
            String value, String pmsgName, String pmsgValue) {
        if (value == null || value.equals(pmsgValue) || value.isEmpty()) {
            hsr.setAttribute(pmsgName, pmsgValue);
            return "";
        }
        return value;
    }
    
    public String getFieldValue(String value, String pmsgValue) {
        if (value == null || value.equals(pmsgValue) || value.isEmpty()) {
            return "";
        }
        return value;
    }

    /**
     * Si son dos las palabras reservadas
     * @param fieldName
     * @param hsr
     * @param inputPromptText
     * @return 
     */
    public String getFieldValue(String fieldName, HttpServletRequest hsr,
            String promptText1, String promptText2) {
        if (hsr.getParameter(fieldName).equals(promptText1)
                || hsr.getParameter(fieldName).equals(promptText2)) {
            return "";
        }
        return hsr.getParameter(fieldName);
    }
}
