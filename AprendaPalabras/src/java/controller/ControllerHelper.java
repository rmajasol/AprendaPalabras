/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author yomac
 */
public class ControllerHelper {
    
//    public ControllerHelper(){
//        HttpSession session = hsr.getSession();
//        User user = (User) session.getAttribute("sessionUser");
//        Language l = new Language(lang, new Date(), user.getId());
//        new LanguageDAO().makePersistent(l);
//    }
    /**
     * Si se presiona el botón se añade a la petición.
     * @param hsr Datos sobre la petición al servidor
     * @param nameButton Valor del atributo 'name' del botón
     * @return 
     */
    public boolean isPressedButton(HttpServletRequest hsr, String nameButton) {
        if(hsr.getParameter(nameButton) != null){
            hsr.setAttribute(nameButton, hsr.getParameter(nameButton));
            return true;
        }
        return false;
    }

    public boolean withURLParam(HttpServletRequest hsr, String param) {
        return hsr.getParameter(param) != null;
    }
    
    public boolean withURLParam(HttpServletRequest hsr, String param, String name) {
        return (hsr.getParameter(param) == null ? 
                name == null : hsr.getParameter(param).equals(name));
    }
    
    public boolean chequedBox(HttpServletRequest hsr, String checkboxName) {
//        String s = hsr.getParameter(checkboxName);
        return hsr.getParameter(checkboxName) != null;
    }
    
    public ModelAndView getView(ModelAndView mv, String defaultView){
        return mv != null ? mv : new ModelAndView(defaultView);
    }
}
