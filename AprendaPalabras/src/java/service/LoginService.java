/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.dao.UserDAO;
import form.singleFields.FieldPassword;
import form.singleFields.FieldUsername;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import utils.Encripter;
import error.ErrorMsgs;
import exceptions.LoginIncorrectException;
import org.springframework.web.servlet.ModelAndView;
import utils.words.Fnames;
import utils.words.Urls;
import utils.words.Views;

/**
 *
 * @author yomac
 */
public class LoginService extends ServiceHelper {

    public LoginService(HttpServletRequest hsr, HttpServletResponse hsr1) {
        super(hsr, hsr1);
    }

    /**
     * Si existe y está verificado entonces se le crea la sesión que contiene su objeto user
     * junto con la cookie con la id de la sesión si dejó marcada la casilla "no cerrar sesión"
     * @param hsr
     * @throws Exception 
     */
    public ModelAndView authenticateUser() {
        try {
            checkForm();
            Criterion c1 = Restrictions.eq("username", hsr.getParameter("username")).ignoreCase();
            /*pasa la contraseña a minúsculas y la encripta comparándola con la 
             * que ya hay en la BD para que así no haya problema con las mayusc*/
            String passwordEnc = Encripter.encPassword(hsr.getParameter("password"));
            Criterion c2 = Restrictions.eq("password", passwordEnc);
            user = new UserDAO().findUniqueByCriteria(c1, c2);
            if (user == null) {
                //si el usuario existe pero la contraseña está mal
                if (new UserDAO().findUniqueByCriteria(c1) != null) {
                    throw new LoginIncorrectException(ErrorMsgs.PASSWORD_INCORRECT);
                }
                throw new LoginIncorrectException(ErrorMsgs.LOGIN_INCORRECT);
            }
            if (!user.getValidated()) {
                throw new LoginIncorrectException(ErrorMsgs.USER_NOT_VALIDATED);
            }
            session = hsr.getSession();
            session.setAttribute("sessionUser", user);

            //si se marcó la casilla de "no cerrar sesión" se guarda en una cookie
            if (hsr.getParameter("dont_close") != null) {
                addCookie(hsr1);
            }

            //redirigimos a la página de búsqueda si todo ha ido bien
            hsr1.sendRedirect(Urls.SEARCH);
        } catch (Exception ex) {
            errors.setLogicError(ex.getMessage());
            loadFocus(Fnames.USERNAME);
            return new ModelAndView(Views.LOGIN);
        }
        return null;
        /**
         * In Java SE 7 and later, a single catch block can handle more than one type 
         * of exception. This feature can reduce code duplication and lessen the temptation 
         * to catch an overly broad exception.
         * http://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html
         */
    }

    public void addCookie(HttpServletResponse hsr1) {
        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        sessionCookie.setMaxAge(365 * 24 * 60 * 60);
        hsr1.addCookie(sessionCookie);
    }

    public void checkForm() throws LoginIncorrectException {
        new FieldUsername(hsr).checkToLogin();
        new FieldPassword(hsr).checkToLogin();
    }
}