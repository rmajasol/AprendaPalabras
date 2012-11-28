/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import service.SearchService;

/**
 * 
 * @author rmajasol
 */
public class SearchController extends ControllerHelper implements Controller {

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        
        SearchService sService = new SearchService(hsr, hsr1);

        if (isPressedButton(hsr, "search_button")) {
            return sService.searchTranslation();
        }

        if (isPressedButton(hsr, "addLang_button_yes")) {
            return sService.createLanguage();
        }
        if (isPressedButton(hsr, "addLang_button_no")) {
            sService.clearNewLangs();
        }

        if (isPressedButton(hsr, "add_tr_button")) {
            return sService.createTranslation();
        }



        if (withURLParam(hsr, "action", "deleteTr")) {
            sService.deleteTranslation();
            return new ModelAndView("search/addTranslation");
        }


        /**
         * AÑADIR / QUITAR DE LA LISTA PERSONAL
         */
        if (withURLParam(hsr, "action", "addTr")) {
            return sService.addToMyList();
        }

        if (withURLParam(hsr, "action", "remTr")) {
            return sService.removeFromMyList();
        }

        /**
         * ACEPCIONES
         */
        if (withURLParam(hsr, "action", "createAccep")) {
            return sService.displayCreateAccepForm();
        }

        if (isPressedButton(hsr, "create_accep_button")) {
            return sService.createAcceptation();
        }

        /**
         * 
         * 
         * CONTEXTOS
         */
        if (withURLParam(hsr, "action", "createContext")) {
            return sService.displayCreateContextForm();
        }

        if (isPressedButton(hsr, "create_context_button")) {
            return sService.createContext();
        }
        
        if (withURLParam(hsr, "action", "remContext")) {
            return sService.deleteContext();
        }
        
        if (withURLParam(hsr, "action", "editContext")) {
            return sService.showEditContextForm();
        }
        
        if (isPressedButton(hsr, "edit_context_button")) {
            return sService.editContext();
        }
        
        /**
         * 
         * REPORTAR
         */
        if (withURLParam(hsr, "action", "reportContext")) {
            return sService.reportContext();
        }
        
        if (withURLParam(hsr, "action", "unreportContext")) {
            return sService.unreportContext();
        }
        

        return sService.displaySearchBox();
    }
}
