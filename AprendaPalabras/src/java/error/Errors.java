/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import java.util.HashMap;

/**
 *
 * @author yomac
 */
public class Errors {

    private HashMap<String, String> formErrors = new HashMap<String, String>();
    private String logicError = null;
    

    /*
     * FORM ERRORS METHODS
     * 
     */
    public HashMap<String, String> getFormErrors() {
        return formErrors;
    }

    public void putWrongField(String fieldName, String fieldError) {
        formErrors.put(fieldName, fieldError);
    }

    public boolean hasFormErrors() {
        return !formErrors.isEmpty();
    }

    /**
     * LOGIC ERRORS METHODS
     * 
     */
    public String getLogicError() {
        return logicError;
    }

    public void setLogicError(String logicError) {
        this.logicError = logicError;
    }

    public boolean hasLogicError() {
        return !(logicError == null);
    }

    /**
     * HAS ERRORS METHOD
     * 
     */
    public boolean hasErrors() {
        return hasFormErrors() || hasLogicError();
    }
}
