/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import service.ConfigService;
import utils.Msgs;

/**
 *
 * @author yomac
 */
public class ConfigController extends ControllerHelper implements Controller {

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        ConfigService cService = new ConfigService(hsr, hsr1);

        /**
         * DARSE DE BAJA
         */
        if (withURLParam(hsr, "action", "unsuscribe")) {
            if (isPressedButton(hsr, "unsuscription_button")) {
                cService.unsuscribe();
            }
            hsr.setAttribute("opt1", Msgs.UNSUSCR_OPT1);
            hsr.setAttribute("opt2", Msgs.UNSUSCR_OPT2);
            return new ModelAndView("config/unsuscribe_dialog");
        }
        
        /**
         * GUARDAR LA CONFIGURACIÓN
         */
        if(isPressedButton(hsr, "save_config_button")){
            cService.saveConfiguration();
            hsr1.sendRedirect("search.htm");
        }
        
        return cService.displayConfiguration();
    }
}
