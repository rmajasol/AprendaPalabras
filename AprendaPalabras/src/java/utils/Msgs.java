/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import data.dao.TranslationDAO;
import form.compounds.TranslationSearch;

/**
 *
 * @author yomac
 */
public class Msgs {
    
    /**
     * 
     * MENSAJES DE INFORMACIÓN
     */
    public static final String TR_CREATED =
            "Traducción creada correctamente";
    public static final String TR_ADDED_TO_LIST =
            "Traducción añadida a mi lista personal";
    public static final String TR_REMOVED_FROM_LIST =
            "Traducción eliminada de mi lista personal";

    
    /**
     * 
     * MENSAJES PARA LA BAJA
     */
    public static final String UNSUSCR_OPT1 = "La aplicación es difícil de manejar";
    public static final String UNSUSCR_OPT2 = "Esperaba aprender más fácilmente";
    
    
    
    

    /**
     * Crea un texto de diálogo para confirmar que se añadan los nuevos lenguajes
     * @param ts
     * @return 
     */
    public static String newLangMsgDialog(TranslationSearch ts) {
        String lang1 = "", lang2 = "",
                msg = "¿Desea agregar a la aplicación ";
        if (ts.getLangFrom().isNewLang()) {
            lang1 = ts.getLangFrom().getFieldValue();
        }
        if (ts.getLangTo().isNewLang()) {
            lang2 = ts.getLangTo().getFieldValue();
        }
        if (!lang1.isEmpty() && !lang2.isEmpty()) {
            msg += "el " + lang1 + " y el " + lang2 + "?";
        } else {
            msg += "el " + (lang1.isEmpty() ? lang2 : lang1) + "?";
        }
        return msg;
    }

    
}
