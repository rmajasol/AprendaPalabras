/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.RegistrationController;
import data.VO.TrContextVO;
import data.VO.TranslationVO;
import exceptions.WrongFormException;
import data.dao.LanguageDAO;
import data.dao.ReportDAO;
import data.dao.TrContextDAO;
import data.dao.TranslationDAO;
import data.dao.UserHasTranslationDAO;
import data.model.User;
import exceptions.NewLanguageException;
import exceptions.TranslationExistsException;
import form.compounds.TranslationSearch;
import form.singleFields.FieldAcceptation;
import form.singleFields.FieldWord;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import utils.Common;
import utils.words.Names;
import utils.Msgs;
import utils.words.Fnames;
import utils.words.Pmsgs;
import utils.words.Views;

/**
 * 
 * @author rmajasol
 */
public class SearchService extends ServiceHelper {

    static final String WITHOUT_TRANSLATIONS = "Sin traducciones";
    static final String NEW_LANG = "Se ha introducido un nuevo lenguaje.";
//    static final String NEW_WORD = "Se ha introducido una nueva palabra.";
    TranslationSearch ts;
    int userId;

    public SearchService(HttpServletRequest hsr, HttpServletResponse hsr1) {
        super(hsr, hsr1);
        userId = getUserFromSession().getId();
    }

    /**
     * 
     * @return 
     */
    public ModelAndView searchTranslation() {
        ts = new TranslationSearch(hsr, errors);
        try {
            ts.checkForm(hsr, errors);

            if (ts.hasNewLang()) {
                throw new NewLanguageException();
            }

            loadFoundTranslations(ts);

            hsr.setAttribute("wordTo_pmsg", Pmsgs.wordTo_pmsg(ts));
            hsr.setAttribute("acceptation_pmsg", Pmsgs.accep_pmsg(ts,
                    user.getInvertAcceptations()));
            hsr.setAttribute("trButton_name", Pmsgs.addTr_buttonName(ts));
        } catch (WrongFormException ex) {
            errors.setLogicError(ex.getMessage());
            loadFocus(Fnames.WORD_FROM);

            return new ModelAndView(Views.SEARCH__SEARCH_TR);
        } catch (NewLanguageException ex) {
            hsr.setAttribute("newLangDialog_msg", Msgs.newLangMsgDialog(ts));

            return new ModelAndView(Views.SEARCH__NEW_LANG_DIALOG);
        }
        //pasamos el foco para que el cursor se ponga autom. en el campo de traducción
        hsr.setAttribute("focus", Fnames.WORD_TO);

        return new ModelAndView(Views.SEARCH__SEARCH_RESULT);
    }

    /**
     * Añade a la petición la lista de traducciones poniendo a 'true' las agregadas
     * a nuestra lista personal
     * @param ts
     * @return 
     */
    public void loadFoundTranslations(TranslationSearch ts) {
        List<TranslationVO> trList = new TranslationDAO().findTranslations(ts);
        setAndLoad(trList);
    }

    /**
     * se carga la lista de traducciones a partir de la traducción a buscar "ts" 
     * (translation search)
     * @param trs 
     */
    public void loadFoundTranslations(TranslationVO trs) {
        List<TranslationVO> trList =
                new TranslationDAO().listTranslations(trs.getWordFrom(),
                trs.getLangFrom(), trs.getLangTo());
        setAndLoad(trList);
    }

    /**
     * Setea cada traducción con:
     * 1) información sobre si el usuario la añadió o no, 
     * 2) los reportes para: traducción, acepciones y cada frase de contexto. 
     * Finalmente carga el VO en la request
     * @param trList 
     */
    public void setAndLoad(List<TranslationVO> trList) {
        new TranslationDAO().setCreated(trList, userId);
        new UserHasTranslationDAO().setAdded(trList, userId);
        new ReportDAO().setReported(trList, userId);
        hsr.setAttribute("trList", trList);
    }

    /**
     * Añade a la B.D. los lenguajes que sean nuevos
     */
    public ModelAndView createLanguage() {
        String msg = "";
        String wordFrom = hsr.getParameter("word_from");
        String selectedFrom = hsr.getParameter("selected_from");
        String typedFrom = hsr.getParameter("typed_from");
        String selectedTo = hsr.getParameter("selected_to");
        String typedTo = hsr.getParameter("typed_to");
        String langFrom = null, langTo = null;

        if (!typedFrom.isEmpty() && !typedTo.isEmpty()) {
            saveLanguage(typedFrom);
            saveLanguage(typedTo);
            msg += "Se han añadido los idiomas " + typedFrom
                    + (typedTo.charAt(0) == 'I' ? " e " : " y ") + typedTo;
            hsr.setAttribute("selected_from", typedFrom);
            hsr.setAttribute("selected_to", typedTo);
            langFrom = typedFrom;
            langTo = typedTo;
        } else {
            msg += "Se ha añadido el idioma ";
            if (!typedFrom.isEmpty()) {
                saveLanguage(typedFrom);
                msg += typedFrom;
                hsr.setAttribute("selected_from", typedFrom);
                hsr.setAttribute("selected_to", selectedTo);
                langFrom = typedFrom;
                langTo = selectedTo;
            }
            if (!typedTo.isEmpty()) {
                saveLanguage(typedTo);
                msg += typedTo;
                hsr.setAttribute("selected_from", selectedFrom);
                hsr.setAttribute("selected_to", typedTo);
                langFrom = selectedFrom;
                langTo = typedTo;
            }
        }
        hsr.setAttribute("newLangDialogResult_msg", msg);
        Common.loadLangList(hsr);
        return searchTranslation();
    }

    public boolean isNewLang(String language) {
        return !new LanguageDAO().exists("language", language);
    }

    public boolean langExists(String language) {
        return new LanguageDAO().exists("language", language);
    }

    public void saveLanguage(String lang) {
        new LanguageDAO().createLanguageIfNotExists(lang, user.getId());
    }

    /**
     * muestra el formulario de búsqueda borrando los lenguajes que hayan
     * nuevos
     */
    public void clearNewLangs() {
        String typedFrom = hsr.getParameter("typed_from");
        String typedTo = hsr.getParameter("typed_to");
        String msg = "";
        hsr.setAttribute("selected_from", hsr.getParameter("selected_from"));
        hsr.setAttribute("selected_to", hsr.getParameter("selected_to"));
        hsr.setAttribute("word_from", hsr.getParameter("word_from"));

        if (!typedFrom.isEmpty() && !typedTo.isEmpty()) {
            msg += "Ha elegido no agregar los idiomas " + typedFrom
                    + (typedTo.charAt(0) == 'I' ? " e " : " y ") + typedTo;
        } else {
            msg += "Ha elegido no agregar el ";
            if (!typedFrom.isEmpty()) {
                msg += typedFrom;
            }
            if (!typedTo.isEmpty()) {
                msg += typedTo;

            }
        }
        hsr.setAttribute("newLangDialogResult_msg", msg);
    }

    public ModelAndView createTranslation() {
        ts = new TranslationSearch(hsr, errors);
        FieldWord wordTo = new FieldWord(hsr, "word_to", Pmsgs.wordTo_pmsg(ts));
        FieldAcceptation acceptation = new FieldAcceptation(hsr,
                Pmsgs.ACCEPTATION, Pmsgs.accep_pmsg(ts, user.getInvertAcceptations()));
        try {
            ts.checkForm(hsr, errors);
            wordTo.check(hsr, errors);
            acceptation.check(hsr, errors);
            if (errors.hasFormErrors()) {
                throw new WrongFormException();
            }
            if (ts.hasNewLang()) {
                throw new NewLanguageException();
            }
            //cargamos la lista anterior por si salta la excepción
            hsr.setAttribute("trList", new TranslationDAO().findTranslations(ts));

            new TranslationDAO().createTranslation(ts, wordTo, acceptation, user);

            hsr.removeAttribute("word_from");
            hsr.removeAttribute("word_to");
            hsr.removeAttribute("acceptation");
            //actualizamos la lista con la traducción nueva
            loadFoundTranslations(ts);
            //cargamos el mensaje de que se creó correctamente
            hsr.setAttribute("trCreated_msg", Msgs.TR_CREATED);

        } catch (WrongFormException ex) {
            hsr.setAttribute(Names.FOCUS, Fnames.WORD_TO);
            return new ModelAndView("search/searchResult");
        } catch (NewLanguageException ex) {
            hsr.setAttribute("newLangDialog_msg", Msgs.newLangMsgDialog(ts));
            return new ModelAndView("search/newLangDialog");
        } catch (TranslationExistsException ex) {
            hsr.removeAttribute("word_to");
            hsr.removeAttribute("acceptation");
            errors.putWrongField(wordTo.getFieldName(), ex.getMessage());
        } finally {
            hsr.setAttribute(Pmsgs.WORD_TO, Pmsgs.wordTo_pmsg(ts));
            hsr.setAttribute(Pmsgs.ACCEPTATION, Pmsgs.accep_pmsg(ts,
                    user.getInvertAcceptations()));
            loadFoundTranslations(ts);
        }
        return searchTranslation();
    }

    public void deleteTranslation() {
    }

    public ModelAndView addToMyList() {
        int trId = Integer.parseInt(hsr.getParameter("tr"));
        String wordFrom = hsr.getParameter(Fnames.WORD_FROM);
        String langFrom = hsr.getParameter(Fnames.SELECTED_FROM);

        new UserHasTranslationDAO().addTrToUserList(userId, trId);

        TranslationVO tr = new TranslationDAO().getTranslation(trId, wordFrom, langFrom);
        loadSearchResultData(tr);
        return new ModelAndView("search/searchResult");
    }

    public ModelAndView removeFromMyList() {
        int trId = Integer.parseInt(hsr.getParameter("tr"));
        String wordFrom = hsr.getParameter(Fnames.WORD_FROM);
        String langFrom = hsr.getParameter(Fnames.SELECTED_FROM);

        new UserHasTranslationDAO().removeTrFromUserList(userId, trId);

        TranslationVO tr = new TranslationDAO().getTranslation(trId, wordFrom, langFrom);
        loadSearchResultData(tr);
        return new ModelAndView("search/searchResult");
    }

    public ModelAndView createAcceptation() {
        TranslationVO tr = getTranslationVO(hsr);

        try {
            String accepFromValue = hsr.getParameter(Fnames.ACCEP_FROM);
            String accepToValue = hsr.getParameter(Fnames.ACCEP_TO);

            if (!hsr.getParameter(Names.CREATE_ACCEP_FROM).isEmpty()) {
                FieldAcceptation accepFrom = new FieldAcceptation(hsr, Fnames.ACCEP_FROM,
                        Pmsgs.ACCEP_FROM, Pmsgs.accep_pmsg2(tr.getLangFrom()));
                accepFrom.check(hsr, errors);
                accepFromValue = accepFrom.getFieldValue();
            }
            if (!hsr.getParameter(Names.CREATE_ACCEP_TO).isEmpty()) {
                FieldAcceptation accepTo = new FieldAcceptation(hsr, Fnames.ACCEP_TO,
                        Pmsgs.ACCEP_TO, Pmsgs.accep_pmsg2(tr.getLangTo()));
                accepTo.check(hsr, errors);
                accepToValue = accepTo.getFieldValue();
            }
            if (errors.hasFormErrors()) {
                throw new WrongFormException();
            }

            new TranslationDAO().updateTranslation(tr, accepFromValue,
                    accepToValue, userId);

        } catch (WrongFormException ex) {
            displayCreateAccepForm();
        } finally {
            loadSearchResultData(tr);
            return new ModelAndView("search/searchResult");
        }
    }

    public ModelAndView displayCreateAccepForm() {
        int trId = Integer.parseInt(hsr.getParameter("tr"));
        String wordFrom = hsr.getParameter(Fnames.WORD_FROM);
        String langFrom = hsr.getParameter(Fnames.SELECTED_FROM);
        TranslationVO tr = new TranslationDAO().getTranslation(trId, wordFrom, langFrom);

        //cargamos los campos según hagan falta
        String accepFrom = tr.getAccepFrom();
        String accepTo = tr.getAccepTo();

        if (accepFrom == null) {
            hsr.setAttribute(Names.CREATE_ACCEP_FROM, true);
        } else {
            hsr.setAttribute(Fnames.ACCEP_FROM, tr.getAccepFrom());
        }
        if (accepTo == null) {
            hsr.setAttribute(Names.CREATE_ACCEP_TO, true);
        } else {
            hsr.setAttribute(Fnames.ACCEP_TO, tr.getAccepTo());
        }

        loadSearchResultData(tr);

        hsr.setAttribute(Pmsgs.ACCEP_FROM, Pmsgs.accep_pmsg2(tr.getLangFrom()));
        hsr.setAttribute(Pmsgs.ACCEP_TO, Pmsgs.accep_pmsg2(tr.getLangTo()));

        return new ModelAndView("search/searchResult");
    }

    public ModelAndView displayCreateContextForm() {
        int trId = Integer.parseInt(hsr.getParameter("tr"));
        String wordFrom = hsr.getParameter(Fnames.WORD_FROM);
        String langFrom = hsr.getParameter(Fnames.SELECTED_FROM);
        TranslationVO tr = new TranslationDAO().getTranslation(trId, wordFrom, langFrom);

        loadSearchResultData(tr);

        hsr.setAttribute(Pmsgs.CONTEXT_FROM, Pmsgs.context_pmsg(tr.getLangFrom()));
        hsr.setAttribute(Pmsgs.CONTEXT_TO, Pmsgs.context_pmsg(tr.getLangTo()));

        //cargamos el foco en el primer text area
        hsr.setAttribute(Names.FOCUS, Fnames.CONTEXT_FROM);

        return new ModelAndView("search/searchResult");
    }

    public ModelAndView createContext() {
        TranslationVO tr = getTranslationVO(hsr);

        try {
            FieldWord phraseFrom = new FieldWord(hsr, Fnames.CONTEXT_FROM,
                    Pmsgs.context_pmsg(tr.getLangFrom()));
            FieldWord phraseTo = new FieldWord(hsr, Fnames.CONTEXT_TO,
                    Pmsgs.context_pmsg(tr.getLangTo()));
            phraseFrom.check(hsr, errors);
            phraseTo.check(hsr, errors);
            if (errors.hasFormErrors()) {
                throw new WrongFormException();
            }

            new TrContextDAO().createContext(tr, phraseFrom, phraseTo, userId);

        } catch (WrongFormException ex) {
            displayCreateContextForm();
        } finally {
            loadSearchResultData(tr);
            return new ModelAndView("search/searchResult");
        }
    }

    public ModelAndView showEditContextForm() {
        Integer cId = Integer.parseInt(hsr.getParameter("c"));
        int trId = Integer.parseInt(hsr.getParameter("tr"));
        String wordFrom = hsr.getParameter(Fnames.WORD_FROM);
        String langFrom = hsr.getParameter(Fnames.SELECTED_FROM);
        TranslationVO tr = new TranslationDAO().getTranslation(trId, wordFrom, langFrom);

        //si se trata de editar un contexto
        if (cId != null) {
            List<TrContextVO> cList = tr.getContextList();
            for (TrContextVO trc : cList) {
                if (trc.getId() == cId) {
                    hsr.setAttribute(Fnames.CONTEXT_FROM, trc.getPhraseFrom());
                    hsr.setAttribute(Fnames.CONTEXT_TO, trc.getPhraseTo());
                    break;
                }
            }
        }

        loadSearchResultData(tr);

        hsr.setAttribute(Pmsgs.CONTEXT_FROM, Pmsgs.context_pmsg(tr.getLangFrom()));
        hsr.setAttribute(Pmsgs.CONTEXT_TO, Pmsgs.context_pmsg(tr.getLangTo()));

        //cargamos el foco en el primer text area
        hsr.setAttribute(Names.FOCUS, Fnames.CONTEXT_FROM);

        return new ModelAndView("search/searchResult");
    }

    /**
     * 
     * 
     * 
     * 
     * POR IMPLEMENTAR
     * @return 
     */
    public ModelAndView editContext() {
        Integer cId = Integer.parseInt(hsr.getParameter("c"));
        TranslationVO tr = getTranslationVO(hsr);

        try {
            FieldWord phraseFrom = new FieldWord(hsr, Fnames.CONTEXT_FROM,
                    Pmsgs.context_pmsg(tr.getLangFrom()));
            FieldWord phraseTo = new FieldWord(hsr, Fnames.CONTEXT_TO,
                    Pmsgs.context_pmsg(tr.getLangTo()));
            phraseFrom.check(hsr, errors);
            phraseTo.check(hsr, errors);
            if (errors.hasFormErrors()) {
                throw new WrongFormException();
            }

            new TrContextDAO().updateContext(tr, cId, phraseFrom, phraseTo);

        } catch (WrongFormException ex) {
            displayCreateContextForm();
        } finally {
            loadSearchResultData(tr);
            return new ModelAndView("search/searchResult");
        }
    }

    public ModelAndView deleteContext() {
        int cId = Integer.parseInt(hsr.getParameter("c"));
        new TrContextDAO().deleteContext(cId);
        return searchResult(hsr);
    }

    /**
     * cargamos los lenguajes que hayan marcados por defecto
     * @return 
     */
    public ModelAndView displaySearchBox() {
        if (user.getDefaultLangFrom() != null) {
            String defLangFrom = new LanguageDAO().findById(user.getDefaultLangFrom()).getLanguage();
            hsr.setAttribute(Fnames.SELECTED_FROM, defLangFrom);
        }
        if (user.getDefaultLangTo() != null) {
            String defLangTo = new LanguageDAO().findById(user.getDefaultLangTo()).getLanguage();
            hsr.setAttribute(Fnames.SELECTED_TO, defLangTo);
        }
        hsr.setAttribute(Names.FOCUS, Fnames.WORD_FROM);
        return new ModelAndView(Views.SEARCH__SEARCH_TR);
    }

    /**
     * carga todo lo necesario para mostrar el resultado de la búsqueda a partir
     * de "trs" (translation searched)
     * @param trs 
     */
    private void loadSearchResultData(TranslationVO trs) {
        hsr.setAttribute(Fnames.WORD_FROM, trs.getWordFrom());
        hsr.setAttribute(Fnames.SELECTED_FROM, trs.getLangFrom());
        hsr.setAttribute(Fnames.SELECTED_TO, trs.getLangTo());

        hsr.setAttribute(Pmsgs.WORD_TO, Pmsgs.wordTo_pmsg(trs.getWordFrom()));
        hsr.setAttribute(Pmsgs.ACCEPTATION, Pmsgs.accep_pmsg(trs, user.getInvertAcceptations()));

        loadFoundTranslations(trs);
    }

    private ModelAndView searchResult(HttpServletRequest hsr) {
        TranslationVO trs = getTranslationVO(hsr);

        hsr.setAttribute(Fnames.WORD_FROM, trs.getWordFrom());
        hsr.setAttribute(Fnames.SELECTED_FROM, trs.getLangFrom());
        hsr.setAttribute(Fnames.SELECTED_TO, trs.getLangTo());

        hsr.setAttribute(Pmsgs.WORD_TO, Pmsgs.wordTo_pmsg(trs.getWordFrom()));
        hsr.setAttribute(Pmsgs.ACCEPTATION, Pmsgs.accep_pmsg(trs, user.getInvertAcceptations()));

        loadFoundTranslations(trs);
        return new ModelAndView("search/searchResult");
    }

    private void reloadSearchAttribs(HttpServletRequest hsr) {
        hsr.setAttribute(Fnames.WORD_FROM, hsr.getParameter(Fnames.WORD_FROM));
        hsr.setAttribute(Fnames.SELECTED_FROM, hsr.getParameter(Fnames.SELECTED_FROM));
        hsr.setAttribute(Fnames.SELECTED_TO, hsr.getParameter(Fnames.SELECTED_TO));
    }

    /**
     * devuelve el VO a partir de la request
     * @param hsr
     * @return 
     */
    private TranslationVO getTranslationVO(HttpServletRequest hsr) {
        int trId = Integer.parseInt(hsr.getParameter("tr"));
        String wordFrom = hsr.getParameter(Fnames.WORD_FROM);
        String langFrom = hsr.getParameter(Fnames.SELECTED_FROM);
        return new TranslationDAO().getTranslation(trId, wordFrom, langFrom);
    }

    public ModelAndView reportContext() {
        int cId = Integer.parseInt(hsr.getParameter("c"));

        new ReportDAO().createContextReport(cId, userId);

        return searchResult(hsr);
    }

    public ModelAndView unreportContext() {
        int cId = Integer.parseInt(hsr.getParameter("c"));

        new ReportDAO().deleteContextReport(cId, userId);

        return searchResult(hsr);
    }
}
