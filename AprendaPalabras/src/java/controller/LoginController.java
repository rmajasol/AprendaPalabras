/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import service.LoginService;
import utils.words.Fnames;
import utils.words.Names;
import utils.words.Views;

/**
 *
 * @author yomac
 */
public class LoginController extends ControllerHelper implements Controller {

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        LoginService lService = new LoginService(hsr, hsr1);

        if (isPressedButton(hsr, "login_button")) {
            return lService.authenticateUser();
        }

        hsr.setAttribute(Names.FOCUS, Fnames.USERNAME);
        return new ModelAndView(Views.LOGIN);
    }
}