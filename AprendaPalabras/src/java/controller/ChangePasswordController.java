/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import service.ChangePasswordService;

/**
 *
 * @author yomac
 */
public class ChangePasswordController extends ControllerHelper implements Controller {

    /**
     * <ul><li>Si el parámetro {@code key} tiene algún valor
     * <li>Si se rellena y se pulsa el botón de enviar email con el link para cambiar
     * la contraseña
     * @param hsr
     * @param hsr1
     * @return
     * @throws Exception 
     */
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        ChangePasswordService cpService = new ChangePasswordService(hsr, hsr1);

        if (isPressedButton(hsr, "send_link_button")) {
            cpService.sendLink(hsr);
        } else if (isPressedButton(hsr, "change_pass_button")) {
            cpService.changePassword();
        } else if (isPressedButton(hsr, "resend_link_button")) {
            cpService.resendLink();
        }
        
        return new ModelAndView("changePassword");
    }
}