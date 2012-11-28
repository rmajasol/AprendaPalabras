/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.compounds;

import exceptions.WrongFormException;
import data.dao.TranslationDAO;
import form.singleFields.FieldWord;
import form.interfaces.IForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import error.ErrorMsgs;
import error.Errors;

/**
 *
 * @author yomac
 */
public class TranslationSearch implements IForm {

    private FieldWord wordFrom;
    private LanguageSelection langFrom;
    private LanguageSelection langTo;

    public TranslationSearch(HttpServletRequest hsr, Errors errors) {
        langFrom = new LanguageSelection(hsr, "from");
        langTo = new LanguageSelection(hsr, "to");
        wordFrom = new FieldWord(hsr, "word_from");
    }

    public TranslationSearch() {
    }

    public FieldWord getWordFrom() {
        return wordFrom;
    }

    public LanguageSelection getLangFrom() {
        return langFrom;
    }

    public LanguageSelection getLangTo() {
        return langTo;
    }

    public void checkForm(HttpServletRequest hsr, Errors errors) throws WrongFormException {

        wordFrom.check(hsr, errors);
        
        /**si no se ha seleccionado ningún lenguaje se añade el error y se 
         * termina la comprobación*/
        if (langFrom.isEmpty() && langTo.isEmpty()) {
            throw new WrongFormException(ErrorMsgs.LANG_NOT_SELECTED);
        }
        /**si los lenguajes son los mismos*/
        if(langFrom.getFieldValue().equalsIgnoreCase(langTo.getFieldValue())){
            throw new WrongFormException(ErrorMsgs.SAME_LANG_SELECTION);
        }
        /**de lo contrario alguno ha tenido que elegirse. Si ha sido el origen
         * añadimos el error, comprobamos el destino y lanzamos la excepción
         */
        if (langFrom.isEmpty()) {
            langTo.check(hsr, errors);
            throw new WrongFormException(ErrorMsgs.LANG_FROM_NOT_SELECTED);
        }
        /**caso análogo si ha sido el destino*/
        if (langTo.isEmpty()) {
            langFrom.check(hsr, errors);
            throw new WrongFormException(ErrorMsgs.LANG_TO_NOT_SELECTED);
        }
        /**si los dos están seleccionados los comprobamos*/
        langFrom.check(hsr, errors);
        langTo.check(hsr, errors);
        
        if(errors.hasFormErrors()){
            throw new WrongFormException();
        }
    }

    /**
     * Comprueba si se tecleó algún idioma
     * @return 
     */
    public boolean hasTypedLang() {
        return langFrom.hasTypedLang() || langTo.hasTypedLang();
    }

    /**
     * Comprueba si se ha introducido algún lenguaje nuevo al buscar la palabra
     * @return 
     */
    public boolean hasNewLang() {
        if (!hasTypedLang()) {
            return false;
        }
        return langFrom.isNewLang() || langTo.isNewLang();
    }

    /**
     * Comprueba si se ha introducido alguna palabra que de momento no tiene 
     * ninguna traducción para los lenguajes elegidos
     * @return 
     */
    public boolean hasNewWord() {
        List translations = new TranslationDAO().findTranslations(this);
        if (translations.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean hasLanguagesSelected() {
        return !(langFrom.isEmpty() && langTo.isEmpty());
    }
    
    /**
     * guarda los valores en la petición sin tener que volver a comprobar el formulario
     */
    public void load(HttpServletRequest hsr){
        wordFrom.keepValue2(hsr);
        langFrom.getSelectedLang().keepValue(hsr);
        langTo.getSelectedLang().keepValue(hsr);
    }
}
