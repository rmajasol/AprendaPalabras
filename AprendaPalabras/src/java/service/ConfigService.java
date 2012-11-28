package service;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import data.dao.LanguageDAO;
import data.dao.UnsuscribedDAO;
import data.dao.UserDAO;
import data.model.User;
import form.singleFields.FieldSelectedLang;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import utils.words.Fnames;
import utils.words.Urls;

/**
 *
 * @author yomac
 */
public class ConfigService extends ServiceHelper {

    public ConfigService(HttpServletRequest hsr, HttpServletResponse hsr1) {
        super(hsr, hsr1);
    }

    public void unsuscribe() {
        String feedback = null;
        String redirectedUrl = Urls.INDEX + "?unsuscr=yes";
        if (!hsr.getParameter("other").isEmpty()) {
            feedback = hsr.getParameter("other");
        } else if (hsr.getParameter("option") != null) {
            feedback = hsr.getParameter("option");
        }
        redirectedUrl += (feedback != null) ? "&f=yes" : "";
        
        new UnsuscribedDAO().unsuscribe(user, feedback);

        /**
         * borramos sesión y le devolvemos al índice
         */
        if (session != null) {
            session.invalidate();
        }
        
        try {
            hsr1.sendRedirect(redirectedUrl);
        } catch (IOException ex) {
//            Logger.getLogger(ConfigService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveConfiguration() {
        User u = getUserFromSession();

        //lenguaje origen por defecto
        FieldSelectedLang defLangFrom = new FieldSelectedLang(hsr, Fnames.DEFAULT_LANG_FROM);
        if (!defLangFrom.isEmpty()) {
            if (defLangFrom.isNoneSelected()) {
                u.setDefaultLangFrom(null);
            } else {
                int langId = new LanguageDAO().getId(defLangFrom.getFieldValue());
                u.setDefaultLangFrom(langId);
            }
        }

        //ocultar idioma origen por defecto en las listas
        if (hsr.getParameter(Fnames.HIDE_DEF_FROM) != null) {
            u.setHideDefLangFrom(true);
        } else {
            u.setHideDefLangFrom(false);
        }

        //lenguaje destino por defecto
        FieldSelectedLang defLangTo = new FieldSelectedLang(hsr, Fnames.DEFAULT_LANG_TO);
        if (!defLangTo.isEmpty()) {
            if (defLangTo.isNoneSelected()) {
                u.setDefaultLangTo(null);
            } else {
                int langId = new LanguageDAO().getId(defLangTo.getFieldValue());
                u.setDefaultLangTo(langId);
            }
        }

        //ocultar idioma origen por defecto en las listas
        if (hsr.getParameter(Fnames.HIDE_DEF_TO) != null) {
            u.setHideDefLangTo(true);
        } else {
            u.setHideDefLangTo(false);
        }

        //invertir idioma para la acepción
        if (hsr.getParameter(Fnames.INVERT_ACCEPTATION_LANG) != null) {
            u.setInvertAcceptations(true);
        } else {
            u.setInvertAcceptations(false);
        }

        //auto-añadir a la lista personal todo lo que el usuario crea
        if (hsr.getParameter(Fnames.AUTO_ADDING) != null) {
            u.setAutoAdding(true);
        } else {
            u.setAutoAdding(false);
        }
        
        //ocultar el nombre de usuario
        if (hsr.getParameter(Fnames.HIDE_USERNAME) != null) {
            u.setHideUsername(true);
        } else {
            u.setHideUsername(false);
        }

        new UserDAO().makePersistent(u);
    }

    public ModelAndView displayConfiguration() {
        User u = getUserFromSession();

        //idioma origen por defecto
        if (u.getDefaultLangFrom() != null) {
            String defLangFrom = new LanguageDAO().findById(u.getDefaultLangFrom()).getLanguage();
            hsr.setAttribute(Fnames.DEFAULT_LANG_FROM, defLangFrom);
        }

        //ocultar idioma origen por defecto en las listas
        hsr.setAttribute(Fnames.HIDE_DEF_FROM, u.getHideDefLangFrom());


        //idioma destino por defecto
        if (u.getDefaultLangTo() != null) {
            String defLangTo = new LanguageDAO().findById(u.getDefaultLangTo()).getLanguage();
            hsr.setAttribute(Fnames.DEFAULT_LANG_TO, defLangTo);
        }

        //ocultar idioma destino por defecto en las listas
        hsr.setAttribute(Fnames.HIDE_DEF_TO, u.getHideDefLangTo());

        //invertir idioma para la acepción
        hsr.setAttribute(Fnames.INVERT_ACCEPTATION_LANG, u.getInvertAcceptations());

        //auto-añadir a la lista personal todo lo que el usuario crea
        hsr.setAttribute(Fnames.AUTO_ADDING, u.getAutoAdding());
        
        //auto-añadir a la lista personal todo lo que el usuario crea
        hsr.setAttribute(Fnames.HIDE_USERNAME, u.getAutoAdding());

        return new ModelAndView("config");
    }
}
