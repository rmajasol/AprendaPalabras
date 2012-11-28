/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.words;

import data.VO.TranslationVO;
import data.dao.TranslationDAO;
import form.compounds.TranslationSearch;
import javax.servlet.http.HttpServletRequest;
import utils.*;

/**
 *
 * @author yomac
 */
public class Pmsgs {

    public static final String WORD_FROM = "wordFrom_pmsg";
    public static final String WORD_TO = "wordTo_pmsg";
    public static final String ACCEPTATION = "acceptation_pmsg";
    public static final String ACCEP_FROM = "accepFrom_pmsg";
    public static final String ACCEP_TO = "accepTo_pmsg";
    public static final String PASSWORD_PMSG = "Repita contraseña";
    public static final String EMAIL_PMSG = "Repita email";
    public static final String SELECTED_LANG_PMSG = "Selecc. idioma";
    public static final String TYPED_LANG_PMSG = "Nuevo idioma";
    public static final String WORD_FROM_PMSG =
            "Introduzca una palabra o frase..";
    public static final String CONTEXT_FROM = "contextFrom_pmsg";
    public static final String CONTEXT_TO = "contextTo_pmsg";

    /**
     * Carga en la request los prompt de todos los campos para que
     * aparezcan aunque no se haya interactuado con la página xej pulsando un botón
     */
    public static void loadPromptMsgs(HttpServletRequest hsr) {
        hsr.setAttribute("password_pmsg", PASSWORD_PMSG);
        hsr.setAttribute("email_pmsg", EMAIL_PMSG);
        hsr.setAttribute("selected_lang_pmsg", SELECTED_LANG_PMSG);
        hsr.setAttribute("typedLang_pmsg", TYPED_LANG_PMSG);
        hsr.setAttribute("wordFrom_pmsg", WORD_FROM_PMSG);
    }

    public static String wordTo_pmsg(TranslationSearch ts) {
        boolean hasTranslation = new TranslationDAO().hasTranslation(ts);
        return (hasTranslation ? "Otra t" : "T") + "raducción para "
                + Common.firstLowerCase(ts.getWordFrom().getFieldValue()) + "..";
    }

    public static String wordTo_pmsg(String wordFrom) {
        return "Otra traducción para " + Common.firstLowerCase(wordFrom) + "..";
    }

    public static String accep_pmsg(TranslationVO tr, boolean invertedAccepLang) {
        String lang = invertedAccepLang ? tr.getLangTo() : tr.getLangFrom();
        return "Para la acepción.. (en " + Common.firstLowerCase(lang) + ")";
    }
    
    public static String accep_pmsg(String lang) { 
        return "Para la acepción.. (en " + Common.firstLowerCase(lang) + ")";
    }

    public static String accep_pmsg(TranslationSearch ts, boolean invertedAccepLang) {
        String lang = invertedAccepLang ? ts.getLangTo().getFieldValue()
                : ts.getLangFrom().getFieldValue();
        return accep_pmsg(lang);
    }

    public static String accep_pmsg2(String lang) {
        return "Acepc. en " + Common.firstLowerCase(lang);
    }

    public static String word_pmsg(String lang) {
        return "Palabra en " + Common.firstLowerCase(lang);
    }

    public static Object addTr_buttonName(TranslationSearch ts) {
        boolean hasTranslation = new TranslationDAO().hasTranslation(ts);
        return "Añadir " + (hasTranslation ? "otra" : "");
    }

    public static String context_pmsg(String lang) {
        return "Introd. frase en " + Common.firstLowerCase(lang) + "..";
    }
}
