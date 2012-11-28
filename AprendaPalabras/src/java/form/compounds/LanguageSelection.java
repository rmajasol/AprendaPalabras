/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.compounds;

import error.ErrorMsgs;
import error.Errors;
import exceptions.WrongFieldException;
import form.singleFields.FieldTypedLang;
import form.singleFields.FieldSelectedLang;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public class LanguageSelection {

    private FieldSelectedLang selectedLang;
    private FieldTypedLang typedLang;

    /**
     * para ver si estamos hablando de elegir varios lenguajes en un mismo formulario, 
     * por ejemplo para traducir de un idioma a otro
     * @param hsr
     * @param selectedLang_fieldName
     * @param typedLang_fieldName
     * @param selectionName 
     */
    public LanguageSelection(HttpServletRequest hsr, String selectionName) {
        selectedLang = new FieldSelectedLang(hsr, "selected_" + selectionName);
        typedLang = new FieldTypedLang(hsr, "typed_" + selectionName);
    }

    public LanguageSelection(HttpServletRequest hsr) {
        selectedLang = new FieldSelectedLang(hsr, "selected_lang");
        typedLang = new FieldTypedLang(hsr, "typed_lang");
    }

    public FieldSelectedLang getSelectedLang() {
        return selectedLang;
    }

    public FieldTypedLang getTypedLang() {
        return typedLang;
    }

    /**
     * tiene prioridad el escrito
     * @return 
     */
    public String getFieldValue() {
        return !typedLang.isEmpty()
                ? typedLang.getFieldValue() : selectedLang.getFieldValue();
    }

    public boolean hasSelectedLang() {
        return !selectedLang.isEmpty();
    }

    public boolean hasTypedLang() {
        return !typedLang.isEmpty();
    }

    public boolean isEmpty() {
        return !hasSelectedLang() && !hasTypedLang();
    }

    public boolean isNewLang() {
        return typedLang.isNewLang();
    }
//    public void clearSelection() {
//        selectedLang.setFieldValue(FieldLangSelector.NOT_SELECTED);
//    }

    public void check(HttpServletRequest hsr, Errors errors) {
        try {
            if (typedLang.isEmpty()) {
                if (selectedLang.isEmpty()) {
                    throw new WrongFieldException(ErrorMsgs.LANG_NOT_SELECTED);
                }
                selectedLang.keepValue(hsr);
                return;
            }
            typedLang.check(hsr, errors);
            /**si no es nuevo se mantiene en el selector, si es nuevo se 
            mantiene en el typed*/
            if (!typedLang.isNewLang()) {
                String tLang = typedLang.getFieldValue();
                //si no es nuevo lo pasamos al selector
                hsr.setAttribute(selectedLang.getFieldName(), tLang);
                hsr.setAttribute(typedLang.getFieldName() + "_exists",
                        tLang + " ya existe");
            } else {
                typedLang.keepValue(hsr);
            }
        } catch (WrongFieldException ex) {
            errors.putWrongField(typedLang.getFieldName(), ex.getMessage());
        }
    }
}