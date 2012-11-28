/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import service.ListsService;

/**
 *
 * @author yomac
 */
public class ListsController extends ControllerHelper implements Controller {

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        /** sólo si el usuario está identificado puede acceder a search.htm*/
        ListsService lService = new ListsService(hsr, hsr1);


        if (withURLParam(hsr, "view", "addedTrs")
                || hsr.getParameter("view") == null) {
            if (withURLParam(hsr, "learned", "yes")) {
                lService.markAsLearned();
            } else if (withURLParam(hsr, "learned", "no")) {
                lService.markAsNOTLearned();
            }
            lService.displayAddedTranslations();
            return new ModelAndView("lists/addedTranslations");
        }


        if (withURLParam(hsr, "view", "createdTrs")) {

            if (isPressedButton(hsr, "edit_tr_button")) {
                return lService.editTranslation();
            }

            if (withURLParam(hsr, "action", "edit")) {
                return lService.displayEditPanel();

            } else if (withURLParam(hsr, "action", "del")) {
                lService.deleteTranslation();

            } else if (withURLParam(hsr, "action", "undoDel")) {
                return lService.undoDeletion();
            }

            return lService.displayCreatedTranslations();
        }

        hsr1.sendRedirect("lists.htm?view=addedTranslations");
        return null;
    }
}
