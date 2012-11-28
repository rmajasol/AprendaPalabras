/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author yomac
 */
public class WrongFieldException extends Exception {
    
    private String fieldName;

    public WrongFieldException(String errorMsg) {
        super(errorMsg);
    }

    public WrongFieldException() {
    }
    
    public WrongFieldException(String fieldName, String errorMsg){
        super(errorMsg);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    
}
