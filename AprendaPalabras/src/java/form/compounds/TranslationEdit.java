/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.compounds;

import data.VO.TranslationVO;
import exceptions.WrongFormException;
import form.singleFields.FieldWord;
import form.interfaces.IForm;
import javax.servlet.http.HttpServletRequest;
import error.ErrorMsgs;
import error.Errors;
import form.singleFields.FieldAcceptation;
import form.singleFields.FieldSelectedLang;
import org.apache.commons.validator.Msg;
import utils.words.Names;
import utils.Msgs;
import utils.words.Fnames;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class TranslationEdit implements IForm {

    private FieldWord wordFrom;
    private FieldWord wordTo;
    private FieldSelectedLang selFrom;
    private FieldSelectedLang selTo;
    private FieldAcceptation accepFrom;
    private FieldAcceptation accepTo;

    public TranslationEdit(HttpServletRequest hsr, Errors errors) {
        selFrom = new FieldSelectedLang(hsr, Fnames.SELECTED_FROM);
        selTo = new FieldSelectedLang(hsr, Fnames.SELECTED_TO);
    }

    /**
     * usado cuando sólo vamos a editar las acepciones y ya se comprobaron todos los campos
     * @param hsr 
     */
    public TranslationEdit(TranslationVO ctr, String accepFrom, String accepTo) {
        wordFrom = new FieldWord(Fnames.WORD_FROM, ctr.getWordFrom());
        wordTo = new FieldWord(Fnames.WORD_TO, ctr.getWordTo());
        selFrom = new FieldSelectedLang(Fnames.SELECTED_FROM, ctr.getLangFrom());
        selTo = new FieldSelectedLang(Fnames.SELECTED_TO, ctr.getLangTo());
        this.accepFrom = new FieldAcceptation(Fnames.ACCEP_FROM, accepFrom);
        this.accepTo = new FieldAcceptation(Fnames.ACCEP_TO, accepTo);
    }

    public void checkForm(HttpServletRequest hsr, Errors errors) throws WrongFormException {
        /**si los lenguajes son los mismos*/
        if (selFrom.getFieldValue().equalsIgnoreCase(selTo.getFieldValue())) {
            throw new WrongFormException(ErrorMsgs.SAME_LANG_SELECTION);
        }
        selFrom.keepValue(hsr);
        selTo.keepValue(hsr);

        String langFrom = selFrom.getFieldValue();
        wordFrom = new FieldWord(hsr, Fnames.WORD_FROM, Pmsgs.word_pmsg(langFrom));
        wordFrom.check(hsr, errors);
        accepFrom = new FieldAcceptation(hsr, Fnames.ACCEP_FROM,
                Pmsgs.ACCEP_FROM, Pmsgs.accep_pmsg2(langFrom));
        accepFrom.check(hsr, errors);

        String langTo = selTo.getFieldValue();
        wordTo = new FieldWord(hsr, Fnames.WORD_TO, Pmsgs.word_pmsg(langTo));
        wordTo.check(hsr, errors);
        accepTo = new FieldAcceptation(hsr, Fnames.ACCEP_TO,
                Pmsgs.ACCEP_TO, Pmsgs.accep_pmsg2(langTo));
        accepTo.check(hsr, errors);

        if (errors.hasFormErrors()) {
            throw new WrongFormException();
        }
    }

    public FieldAcceptation getAccepFrom() {
        return accepFrom;
    }

    public FieldAcceptation getAccepTo() {
        return accepTo;
    }

    public FieldSelectedLang getSelFrom() {
        return selFrom;
    }

    public FieldSelectedLang getSelTo() {
        return selTo;
    }

    public FieldWord getWordFrom() {
        return wordFrom;
    }

    public FieldWord getWordTo() {
        return wordTo;
    }
    /**
     * Comprueba si se ha introducido alguna palabra que de momento no tiene 
     * ninguna traducción para los lenguajes elegidos
     * @return 
     */
//    public boolean hasNewWord() {
//        List translations = new TranslationDAO().findTranslations(this);
//        if (translations.isEmpty()) {
//            return true;
//        }
//        return false;
//    }
}
