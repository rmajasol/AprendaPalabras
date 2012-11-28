/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public class SingleField extends Form {
    
    protected String fieldName;
    protected String fieldValue;
    
    /*
     * GETTERS AND SETTERS
     */
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
    
    public void keepValue(HttpServletRequest hsr){
        hsr.setAttribute(fieldName, fieldValue);
    }
    
    public void keepValue2(HttpServletRequest hsr){
        hsr.setAttribute(fieldName, hsr.getAttribute(fieldName));
    }
}
