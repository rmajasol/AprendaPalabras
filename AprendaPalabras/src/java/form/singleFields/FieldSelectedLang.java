/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import form.SingleField;
import javax.servlet.http.HttpServletRequest;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class FieldSelectedLang extends SingleField {

    public FieldSelectedLang(HttpServletRequest hsr, String fieldName) {
        this.fieldName = fieldName;
        fieldValue = getFieldValue(hsr.getParameter(fieldName),
                Pmsgs.SELECTED_LANG_PMSG);
    }

    public FieldSelectedLang(HttpServletRequest hsr) {
        this.fieldName = "selected_lang";
        fieldValue = getFieldValue(hsr.getParameter(fieldName),
                Pmsgs.SELECTED_LANG_PMSG);
    }
    
    public FieldSelectedLang(String fieldName, String fieldValue){
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public boolean isEmpty() {
        return fieldValue.isEmpty();
    }
    
    public boolean isNoneSelected() {
        return fieldValue.equals("none");
    }
}