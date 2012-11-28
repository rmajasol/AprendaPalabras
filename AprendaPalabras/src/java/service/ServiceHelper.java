/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.dao.LanguageDAO;
import data.model.User;
import error.Errors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Common;
import utils.words.Fnames;
import utils.words.Names;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class ServiceHelper {

    protected HttpServletRequest hsr;
    protected HttpServletResponse hsr1;
    protected HttpSession session;
    protected Errors errors = new Errors();
    protected User user;

    public ServiceHelper(HttpServletRequest hsr, HttpServletResponse hsr1) {
        this.hsr = hsr;
        this.hsr1 = hsr1;
        loadCurrentUrl();
        Pmsgs.loadPromptMsgs(this.hsr);
        Common.loadLangList(hsr);
        this.session = hsr.getSession();
        hsr.setAttribute("errors", this.errors);
        this.user = getUserFromSession();
    }

    public ServiceHelper() {
    }

    public Errors getErrors() {
        return errors;
    }

    public HttpServletRequest getHsr() {
        return hsr;
    }

    public final User getUserFromSession() {
        session = hsr.getSession();
        return (User) session.getAttribute("sessionUser");
    }

    public final void loadCurrentUrl() {
        String uri = hsr.getRequestURI();
        Pattern p = Pattern.compile("/.*/(.*)");
        Matcher m = p.matcher(uri);
        String s = null;
        if (m.find()) {
            s = m.group(1);
        }
        hsr.setAttribute("currentUrl", s);
    }

    public String getDefLangFrom() {
        if (user.getDefaultLangFrom() != null) {
            return new LanguageDAO().findById(user.getDefaultLangFrom()).getLanguage();
        }
        return null;
    }

    public String getDefLangTo() {
        if (user.getDefaultLangTo() != null) {
            return new LanguageDAO().findById(user.getDefaultLangTo()).getLanguage();
        }
        return null;
    }
    
    protected void loadDefLangs() {
        hsr.setAttribute(Fnames.DEFAULT_LANG_FROM, getDefLangFrom());
        hsr.setAttribute(Fnames.DEFAULT_LANG_TO, getDefLangTo());
    }
    
    protected void loadFocus(String fieldName) {
        hsr.setAttribute(Names.FOCUS, fieldName);
    }
}
