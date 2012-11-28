/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import javax.servlet.http.HttpServletRequest;
import exceptions.WrongFieldException;
import data.dao.LanguageDAO;
import error.ErrorMsgs;
import error.Errors;
import form.SingleField;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class FieldTypedLang extends SingleField {

    public FieldTypedLang(HttpServletRequest hsr, String fieldName) {
        this.fieldName = fieldName;
        fieldValue = getFieldValue_firstUpp(hsr.getParameter(fieldName), 
                Pmsgs.TYPED_LANG_PMSG);
    }

    public void check(HttpServletRequest hsr, Errors errors) throws WrongFieldException {
        check_empty(fieldValue);
        if (fieldValue.length() > 45) {
            throw new WrongFieldException(ErrorMsgs.OVERSIZED_TYPED_LANG);
        }
    }

    public boolean isEmpty() {
        return fieldValue.isEmpty();
    }

    /**
     * Mira si el lenguaje escrito es nuevo
     * @return 
     */
    public boolean isNewLang() {
        if (isEmpty()){
            return false;
        }
        if (new LanguageDAO().exists("language", fieldValue)) {
            return false;
        }
        return true;
    }
}
