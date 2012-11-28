/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.VO.TranslationVO;
import data.dao.TranslationDAO;
import data.dao.UserHasTranslationDAO;
import error.ErrorMsgs;
import exceptions.BlockedTranslationException;
import exceptions.WrongFormException;
import form.compounds.TranslationEdit;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import utils.Common;
import utils.words.Names;
import utils.words.Fnames;
import utils.words.Pmsgs;

/**
 *
 * @author yomac
 */
public class ListsService extends ServiceHelper {

    int userId;

    public ListsService(HttpServletRequest hsr, HttpServletResponse hsr1) {
        super(hsr, hsr1);
        userId = getUserFromSession().getId();
        super.loadDefLangs();
    }

    public ModelAndView displayAddedTranslations() {
        List<TranslationVO> trList = new UserHasTranslationDAO().listAddedTranslations(userId);
        loadList(trList);
        return new ModelAndView("lists/addedTranslations");
    }

    public void markAsLearned() {
        new UserHasTranslationDAO().markAsLearned(userId, hsr.getParameter("uht"));
    }

    public void markAsNOTLearned() {
        new UserHasTranslationDAO().markAsNOTLearned(userId, hsr.getParameter("uht"));
    }

    public ModelAndView displayCreatedTranslations() {
        List<TranslationVO> trList = new TranslationDAO().listCreatedTranslations(userId);
        loadList(trList);
        return new ModelAndView("lists/createdTranslations");
    }

    public ModelAndView editTranslation() {
        TranslationEdit ted = new TranslationEdit(hsr, errors);
        try {
            ted.checkForm(hsr, errors);
            int trId = Integer.parseInt(hsr.getParameter("tr"));

            new TranslationDAO().updateTranslation(trId, ted, userId, false);

            hsr1.sendRedirect("lists.htm?view=createdTrs");
        } catch (WrongFormException ex) {
            errors.setLogicError(ex.getMessage());
        } catch (BlockedTranslationException ex) {
            errors.setLogicError(ex.getMessage());
        } catch (IOException ex) {
            errors.setLogicError(ErrorMsgs.REDIRECT_ERROR);
        } finally {
            loadEditingTranslation();
            return displayCreatedTranslations();
        }
    }

    public ModelAndView deleteTranslation() {
        try {

            int trId = Integer.parseInt(hsr.getParameter("tr"));

            new TranslationDAO().deleteTranslation(hsr, trId, userId);

        } catch (BlockedTranslationException ex) {
            errors.setLogicError(ex.getMessage());
            displayCreatedTranslations();
        } finally {
            return displayCreatedTranslations();
        }
    }

    public void loadEditingTranslation() {
        int trId = Integer.parseInt(hsr.getParameter("tr"));

        TranslationVO tr = new TranslationDAO().getTranslation(trId);
        hsr.setAttribute(Fnames.WORD_FROM, tr.getWordFrom());
        hsr.setAttribute(Fnames.SELECTED_FROM, tr.getLangFrom());
        hsr.setAttribute(Fnames.ACCEP_FROM, tr.getAccepFrom());

        hsr.setAttribute(Fnames.WORD_TO, tr.getWordTo());
        hsr.setAttribute(Fnames.SELECTED_TO, tr.getLangTo());
        hsr.setAttribute(Fnames.ACCEP_TO, tr.getAccepTo());

        hsr.setAttribute(Pmsgs.WORD_FROM, Pmsgs.word_pmsg(tr.getLangFrom()));
        hsr.setAttribute(Pmsgs.WORD_TO, Pmsgs.word_pmsg(tr.getLangTo()));
        hsr.setAttribute(Pmsgs.ACCEP_FROM, Pmsgs.accep_pmsg2(tr.getLangFrom()));
        hsr.setAttribute(Pmsgs.ACCEP_TO, Pmsgs.accep_pmsg2(tr.getLangTo()));
    }
//    public TranslationVO getEditingCreated() {
//        List<CreatedTrVO> createdTrs =
//                (List<CreatedTrVO>) hsr.getAttribute("createdTrs");
//        int trId = Integer.parseInt(hsr.getParameter("tr"));
//        for (TranslationVO tr : createdTrs) {
//            if (tr.getTrId() == trId) {
//                return tr;
//            }
//        }
//        return null;
//    }

    public ModelAndView undoDeletion() {
        TranslationVO ctr = (TranslationVO) Common.getFromSession(hsr, Names.SESS_DELETED_TR);
        new TranslationDAO().createBackTranslation(ctr, user);
        displayCreatedTranslations();
        Common.removeFromSession(hsr, "deletedTr");
        return new ModelAndView("lists/createdTranslations");
    }

    public ModelAndView displayEditPanel() {
        displayCreatedTranslations();
        loadEditingTranslation();
        return new ModelAndView("lists/createdTranslations");
    }

    /**
     * si el usuario eligió idiomas por defecto para el origen/destino se reordenan
     */
    private void swapToDefLangs(List<TranslationVO> trList) {
        String defLangFrom = getDefLangFrom();
        String defLangTo = getDefLangTo();
        for (TranslationVO tr : trList) {
            tr.swapToDefLangs(defLangFrom, defLangTo);
        }
    }

    /**
     * mira si sólo hay lenguages por defecto en la lista y están marcardos como ocultos
     * en la configuración de usuario
     * @param trList
     * @return 
     */
    private boolean hasAllDefHidden(List<TranslationVO> trList) {
        String defLangFrom = getDefLangFrom();
        String defLangTo = getDefLangTo();
        boolean hideDefFrom = user.getHideDefLangFrom();
        boolean hideDefTo = user.getHideDefLangTo();

        if (!hideDefFrom || !hideDefTo) {
            return false;
        }
        for (TranslationVO tr : trList) {
            if (!(tr.has(defLangFrom) && tr.has(defLangTo))) {
                return false;
            }
        }
        return true;
    }

    private void loadList(List<TranslationVO> trList) {
        swapToDefLangs(trList);
        hsr.setAttribute(Names.HAS_ALL_DEF_AND_HIDDEN, hasAllDefHidden(trList));
        hsr.setAttribute("trList", trList);
    }
}
