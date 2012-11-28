/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import exceptions.TranslationExistsException;
import data.GenericHibernateDAO;
import data.VO.AcceptationDataVO;
import data.VO.TrContextVO;
import data.VO.TranslationVO;
import data.model.Translation;
import data.model.User;
import data.model.UserCreatesTrAccep;
import error.ErrorMsgs;
import exceptions.BlockedTranslationException;
import form.compounds.TranslationEdit;
import form.compounds.TranslationSearch;
import form.singleFields.FieldAcceptation;
import form.singleFields.FieldWord;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import utils.Common;

/**
 *
 * @author yomac
 */
public class TranslationDAO extends GenericHibernateDAO<Translation, Integer> {

    private static final String SQL_JOINS =
            "JOIN WLA as wla1 ON wla1.id = tr.WLA1_id "
            + "LEFT JOIN Acceptation as acc1 ON acc1.id = wla1.Acceptation_id "
            + "JOIN WLA as wla2 ON wla2.id = tr.WLA2_id "
            + "LEFT JOIN Acceptation as acc2 ON acc2.id = wla2.Acceptation_id "
            + ""
            + "JOIN WordLanguage as wl1 ON wl1.id = wla1.WordLanguage_id "
            + "JOIN WordLanguage as wl2 ON wl2.id = wla2.WordLanguage_id "
            + "JOIN Word as w1 ON wl1.Word_id = w1.id "
            + "JOIN Word as w2 ON wl2.Word_id = w2.id "
            + "JOIN Language as l1 ON wl1.Language_id = l1.id "
            + "JOIN Language as l2 ON wl2.Language_id = l2.id "
            + "JOIN User as u ON tr.User_id = u.id ";
    private static final String SQL_LIST_TRANSLATIONS =
            "FROM Translation as tr "
            + ""
            + SQL_JOINS
            + ""
            + "WHERE "
            + "(w1.word = :wordFrom AND l1.language = :languageFrom AND "
            + "l2.language = :languageTo) "
            + "OR "
            + "(w2.word = :wordFrom AND l2.language = :languageFrom AND "
            + "l1.language = :languageTo)";
    private static final String SQL_GET_TRANSLATION =
            "FROM Translation as tr "
            + ""
            + SQL_JOINS
            + ""
            + "WHERE "
            + "tr.id = :trId";

    public List<TranslationVO> findTranslations(TranslationSearch ts) {
        String word = ts.getWordFrom().getFieldValue();
        String langFrom = ts.getLangFrom().getFieldValue();
        String langTo = ts.getLangTo().getFieldValue();
        return listTranslations(word, langFrom, langTo);
    }

    /**
     * mira si lo buscado tiene al menos una traducción
     * @param word
     * @param lFrom
     * @param lTo
     * @return 
     */
    public boolean hasTranslation(String word, String lFrom, String lTo) {
        String sql =
                "SELECT tr.* " + SQL_LIST_TRANSLATIONS;

        Query query = getSession().createSQLQuery(sql).
                addEntity(Translation.class).
                setParameter("wordFrom", word).
                setParameter("languageFrom", lFrom).
                setParameter("languageTo", lTo);
        List l = query.list();
        session.close();
        return l.isEmpty() ? false : true;
    }

    public boolean hasTranslation(TranslationSearch ts) {
        String wFrom = ts.getWordFrom().getFieldValue();
        String lFrom = ts.getLangFrom().getFieldValue();
        String lTo = ts.getLangTo().getFieldValue();
        return hasTranslation(wFrom, lFrom, lTo);
    }

    public Translation findTrById(int trId) {
        String hql = "FROM Translation as tr WHERE tr.id = :trId";
        Query query = getSession().createQuery(hql).
                setParameter("trId", trId);
        Translation t = (Translation) query.uniqueResult();
        session.close();
        return t;
    }

    public List<TranslationVO> listTranslations(String wFrom, String lFrom,
            String lTo) {
        String sql =
                "SELECT tr.id, w1.word, l1.language, acc1.acceptation, "
                + "w2.word, l2.language, acc2.acceptation, tr.creationDate, "
                + "u.username, u.id "
                + SQL_LIST_TRANSLATIONS;

        Query query = getSession().createSQLQuery(sql).
                addScalar("tr.id", Hibernate.INTEGER).
                addScalar("w1.word", Hibernate.STRING).
                addScalar("l1.language", Hibernate.STRING).
                addScalar("acc1.acceptation", Hibernate.STRING).
                addScalar("w2.word", Hibernate.STRING).
                addScalar("l2.language", Hibernate.STRING).
                addScalar("acc2.acceptation", Hibernate.STRING).
                addScalar("tr.creationDate", Hibernate.TIMESTAMP).
                addScalar("u.username", Hibernate.STRING).
                addScalar("u.id", Hibernate.INTEGER).
                setParameter("wordFrom", wFrom).
                setParameter("languageFrom", lFrom).
                setParameter("languageTo", lTo);

        List l = query.list();
        session.close();

        List<TranslationVO> trList = new ArrayList<TranslationVO>();

        Iterator it = l.iterator();
        while (it.hasNext()) {
            Object[] r = (Object[]) it.next();
            int trId = (Integer) r[0];
            String w1 = (String) r[1];
            String l1 = (String) r[2];
            String acc1 = (String) r[3];
            String w2 = (String) r[4];
            String l2 = (String) r[5];
            String acc2 = (String) r[6];
            Date creationDate = (Date) r[7];
            String userCreatorName = (String) r[8];
            int userCreatorId = (Integer) r[9];

            TranslationVO tr = new TranslationVO(trId, w1, l1, acc1,
                    w2, l2, acc2, creationDate, userCreatorName, userCreatorId);

            //invertimos el sentido de la traducción si se busca en el sentido contrario 
            //al que se registró (por ejemplo si se registró gato -> cat y buscamos por cat,
            //que nos salga gato y no cat
            if (!(w1.equals(wFrom) && l1.equals(lFrom))) {
                tr.swap();
            }

            //sacamos su lista de contextos y la agregamos al VO
            List<TrContextVO> cList = new TrContextDAO().getTrContextList(tr.getTrId());
            tr.setContextList(cList);

            //indicamos cuáles ha reportado el usuario y cuáles no


            trList.add(tr);
        }
        return trList;
    }

    public Translation createTranslation(String wordFrom, String wordTo,
            String langFrom, String langTo, String accepFrom, String accepTo,
            User user) throws TranslationExistsException {

        int userId = user.getId();

        //sacamos las id's de las palabras y lenguajes
        int wordFromId = new WordDAO().createIfNotExists(wordFrom, userId);
        int langFromId = new LanguageDAO().createLanguageIfNotExists(langFrom, userId);
        int langToId = new LanguageDAO().createLanguageIfNotExists(langTo, userId);
        int wordToId = new WordDAO().createIfNotExists(wordTo, userId);


        //sacamos las id's de las wl
        int wl1Id = new WordLanguageDAO().createIfNotExists(wordFromId, langFromId, userId);
        int wl2Id = new WordLanguageDAO().createIfNotExists(wordToId, langToId, userId);

        //sacamos las id's de las wla
        int wla1Id = new WlaDAO().createIfNotExists(wl1Id, accepFrom, userId);
        int wla2Id = new WlaDAO().createIfNotExists(wl2Id, accepTo, userId);

        if (hasParentTranslation(wla1Id, wla2Id)) {
            throw new TranslationExistsException(wordFrom, langFrom, langTo, wordTo);
        }

        //y finalmente creamos la traducción, vinculándole la acepción a la wl1 (origen en este caso)
        Translation tr = findUniqueByCriteria(
                Restrictions.eq("wla1Id", wla1Id),
                Restrictions.eq("wla2Id", wla2Id));
        if (tr == null) {
            tr = findUniqueByCriteria(
                    Restrictions.eq("wla2Id", wla1Id),
                    Restrictions.eq("wla1Id", wla2Id));
        }
        //si el par existe lanzamos excepción
        if (tr != null) {
            throw new TranslationExistsException(wordFrom, langFrom, langTo, wordTo);
        }

        //si el par wla no existe se crea una traducción nueva
        tr = new Translation(new Date(), userId, wla1Id, wla2Id);
        return makePersistent(tr);
    }

    public void createTranslation(TranslationSearch ts, FieldWord wTo,
            FieldAcceptation accep, User user)
            throws TranslationExistsException {

        String wordFrom = ts.getWordFrom().getFieldValue();
        String wordTo = wTo.getFieldValue();
        String langFrom = ts.getLangFrom().getFieldValue();
        String langTo = ts.getLangTo().getFieldValue();
        String acc = accep.getFieldValue();

        Translation tr;
        if (user.getInvertAcceptations()) {
            tr = createTranslation(wordFrom, wordTo, langFrom, langTo, "", acc, user);
        } else {
            tr = createTranslation(wordFrom, wordTo, langFrom, langTo, acc, "", user);
        }

        if (user.getAutoAdding()) {
            new UserHasTranslationDAO().addTrToUserList(user.getId(), tr.getId());
        }
    }

    /**
     * crea una traducción con sus contextos a partir del objeto que se guardó
     * en la sesión
     * @param savedTr
     * @param user
     * @throws TranslationExistsException 
     */
    public void createBackTranslation(TranslationVO savedTr, User user) {
        String wordFrom = savedTr.getWordFrom();
        String wordTo = savedTr.getWordTo();
        String langFrom = savedTr.getLangFrom();
        String langTo = savedTr.getLangTo();
        String accepFrom = savedTr.getAccepFrom();
        String accepTo = savedTr.getAccepTo();


        Translation newTr = null;

        try {
            newTr = createTranslation(wordFrom, wordTo, langFrom, langTo,
                    accepFrom, accepTo, user);
        } catch (TranslationExistsException ex) {
        }

        //y creamos todos los contextos que habían
        new TrContextDAO().createBackContexts(savedTr, newTr);
    }

    /**
     * mira si hay alguna traducción con el par wl indicado
     * @param wl1Id
     * @param wl2Id
     * @return 
     */
    private boolean hasParentTranslation(int wla1Id, int wla2Id) {
        String sql = "SELECT tr.* "
                + "FROM Translation tr "
                + "JOIN WLA as wla1 ON wla1.id = tr.WLA1_id "
                + "JOIN WLA as wla2 ON wla2.id = tr.WLA2_id "
                + ""
                + "WHERE "
                + "(wla1.id = :wla1Id AND wla2.id = :wla2Id) "
                + "OR "
                + "(wla1.id = :wla2Id AND wla2.id = :wla1Id)";

        Query query = getSession().createSQLQuery(sql).
                addEntity(Translation.class).
                setParameter("wla1Id", wla1Id).
                setParameter("wla2Id", wla2Id);
        List l = query.list();
        session.close();
        return l.isEmpty() ? false : true;
    }

    public List<TranslationVO> listCreatedTranslations(int userId) {
        String sql =
                "SELECT tr.id, w1.word, l1.language, acc1.acceptation, "
                + "w2.word, l2.language, acc2.acceptation, tr.creationDate "
                + ""
                + "FROM Translation as tr "
                + ""
                + SQL_JOINS
                + ""
                + "WHERE tr.User_id = :userId";

        Query query = getSession().createSQLQuery(sql).
                addScalar("tr.id", Hibernate.INTEGER).
                addScalar("w1.word", Hibernate.STRING).
                addScalar("w2.word", Hibernate.STRING).
                addScalar("l1.language", Hibernate.STRING).
                addScalar("l2.language", Hibernate.STRING).
                addScalar("acc1.acceptation", Hibernate.STRING).
                addScalar("acc2.acceptation", Hibernate.STRING).
                addScalar("tr.creationDate", Hibernate.TIMESTAMP).
                setParameter("userId", userId);
        List l = query.list();
        session.close();

        List<TranslationVO> createdTrList = new ArrayList<TranslationVO>();

        Iterator it = l.iterator();
        while (it.hasNext()) {
            Object[] r = (Object[]) it.next();
            int trId = (Integer) r[0];
            String w1 = (String) r[1];
            String w2 = (String) r[2];
            String l1 = (String) r[3];
            String l2 = (String) r[4];
            String acc1 = (String) r[5];
            String acc2 = (String) r[6];
            Date creationDate = (Date) r[7];
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//            String crDate = sdf.format(cd);

            TranslationVO tr = new TranslationVO(trId, w1, w2, l1, l2, acc1,
                    acc2, creationDate);

            //sacamos su lista de contextos y la agregamos al VO
            List<TrContextVO> cList = new TrContextDAO().getTrContextList(tr.getTrId());
            tr.setContextList(cList);

            createdTrList.add(tr);
        }
        return createdTrList;
    }

    /**
     * aquí no nos importa el sentido de la traducción ya que la vamos a escoger para eliminarla
     * @param trId
     * @return 
     */
    public TranslationVO getTranslation(int trId) {
        return getTranslation(trId, "", "");
    }

    public TranslationVO getTranslation(int trId, String wordFrom, String langFrom) {
        String sql =
                "SELECT tr.id, w1.word, l1.language, acc1.acceptation, "
                + "w2.word, l2.language, acc2.acceptation, tr.creationDate, "
                + "u.username, u.id "
                + ""
                + SQL_GET_TRANSLATION;

        Query query = getSession().createSQLQuery(sql).
                addScalar("tr.id", Hibernate.INTEGER).
                addScalar("w1.word", Hibernate.STRING).
                addScalar("l1.language", Hibernate.STRING).
                addScalar("acc1.acceptation", Hibernate.STRING).
                addScalar("w2.word", Hibernate.STRING).
                addScalar("l2.language", Hibernate.STRING).
                addScalar("acc2.acceptation", Hibernate.STRING).
                addScalar("tr.creationDate", Hibernate.TIMESTAMP).
                addScalar("u.username", Hibernate.STRING).
                addScalar("u.id", Hibernate.INTEGER).
                setParameter("trId", trId);

        Object[] r = (Object[]) query.uniqueResult();
        session.close();
        int id = (Integer) r[0];
        String w1 = (String) r[1];
        String l1 = (String) r[2];
        String acc1 = (String) r[3];
        String w2 = (String) r[4];
        String l2 = (String) r[5];
        String acc2 = (String) r[6];
        Date creationDate = (Date) r[7];
        String userCreatorName = (String) r[8];
        int userCreatorId = (Integer) r[9];

        TranslationVO tr = new TranslationVO(id, w1, l1, acc1, w2, l2, acc2,
                creationDate, userCreatorName, userCreatorId);

        //para el getTranslation donde no nos interesa el sentido de la traducción
        if (wordFrom.isEmpty() && langFrom.isEmpty()) {
            //sacamos su lista de contextos y la agregamos al VO
            List<TrContextVO> cList = new TrContextDAO().getTrContextList(tr.getTrId());
            tr.setContextList(cList);
            return tr;
        }

        if (!(w1.equals(wordFrom) && l1.equals(langFrom))) {
            tr.swap();
        }

        //sacamos su lista de contextos y la agregamos al VO
        List<TrContextVO> cList = new TrContextDAO().getTrContextList(tr.getTrId());
        tr.setContextList(cList);

        return tr;
    }

    public void updateTranslation(TranslationVO tr, String accepFrom, String accepTo,
            int userId) throws BlockedTranslationException {
        TranslationEdit tre = new TranslationEdit(tr, accepFrom, accepTo);
        updateTranslation(tr.getTrId(), tre, userId, true);
    }

    public void updateTranslation(int trId, TranslationEdit te, int userId, boolean onlyAcceptations)
            throws BlockedTranslationException {

        //si se están agregando acepciones no hay que hacer las comprobaciones de seguridad
        if (!onlyAcceptations) {
            checkIfBlocked(trId, userId);
        }

        //datos de la traducción antes de ser editada
        TranslationVO regTr = new TranslationDAO().getTranslation(trId,
                te.getWordFrom().getFieldValue(), te.getSelFrom().getFieldValue());

        String wr_from = regTr.getWordFrom();
        String wf_from = te.getWordFrom().getFieldValue();
        String wr_to = regTr.getWordTo();
        String wf_to = te.getWordTo().getFieldValue();

        String lr_from = regTr.getLangFrom();
        String lf_from = te.getSelFrom().getFieldValue();
        String lr_to = regTr.getLangTo();
        String lf_to = te.getSelTo().getFieldValue();

        String ar_from = regTr.getAccepFrom();
        String af_from = te.getAccepFrom().getFieldValue();
        String ar_to = regTr.getAccepTo();
        String af_to = te.getAccepTo().getFieldValue();

        int old_wlaFrom_id = new WlaDAO().getId(wr_from, lr_from, ar_from);
        int old_wlaTo_id = new WlaDAO().getId(wr_to, lr_to, ar_to);

        int wlaFrom_id = new WlaDAO().createWlaIfNotExists(lr_from, lf_from, wr_from, wf_from,
                ar_from, af_from, userId);
        int wlaTo_id = new WlaDAO().createWlaIfNotExists(lr_to, lf_to, wr_to, wf_to,
                ar_to, af_to, userId);

        /**
         * creamos los registros para dejar constancia del usuario que creó la acepción
         */
        if (onlyAcceptations) {
            Integer accepFromId = new WlaDAO().findById(wlaFrom_id).getAcceptationId();
            if (accepFromId != null) {
                new UserCreatesTrAccepDAO().createEntry(trId, accepFromId, userId);
            }
            Integer accepToId = new WlaDAO().findById(wlaTo_id).getAcceptationId();
            if (accepToId != null) {
                new UserCreatesTrAccepDAO().createEntry(trId, accepToId, userId);
            }
        }

        //actualizamos el id de la nuevas wla en las frases 
        if (old_wlaFrom_id != wlaFrom_id) {
            new PhraseDAO().updateToNewWla(old_wlaFrom_id, wlaFrom_id);
        }
        if (old_wlaTo_id != wlaTo_id) {
            new PhraseDAO().updateToNewWla(old_wlaTo_id, wlaTo_id);
        }

        Translation tr = findById(trId);
        tr.setWla1Id(wlaFrom_id);
        tr.setWla2Id(wlaTo_id);
        makePersistent(tr);

        deleteIfOrphans(regTr);
    }

    public void deleteTranslation(HttpServletRequest hsr, int trId, int userId)
            throws BlockedTranslationException {

        checkIfBlocked(trId, userId);

        //datos de la traducción antes de ser eliminada
        TranslationVO ctr = new TranslationDAO().getTranslation(trId);

        //eliminamos los registros de usuarios que la agregaron
        new UserHasTranslationDAO().removeTrFromUserList(userId, trId);

        /** eliminamos reportes de la traducción */
        new ReportDAO().deleteReports_forTranslation(trId);
        /** eliminamos sus dos posibles uctas */
        new UserCreatesTrAccepDAO().deleteUctas(trId);

        /** eliminamos los contextos */
        new TrContextDAO().deleteContexts(trId);

        Translation tr = findById(trId);
        if (tr != null) {
            makeTransient(tr);
        }
        deleteIfOrphans(ctr);

        Common.saveInSessionUser(hsr, "deletedTr", ctr);
    }

    /**
     * Comprueba si se puede aplicar la acción que sigue al cuerpo de este método
     * 
     * @param trId
     * @param userId
     * @throws BlockedTranslationException 
     */
    public void checkIfBlocked(int trId, int userId) throws BlockedTranslationException {
        if (!new TranslationDAO().isCreatedBy(trId, userId)) {
            throw new BlockedTranslationException(ErrorMsgs.TRANSLATION_NOT_CREATED_BY);
        }
        if (new UserHasTranslationDAO().isAddedByOthers(trId, userId)) {
            throw new BlockedTranslationException(ErrorMsgs.USER_HAS_TRANSLATION);
        }
        if (!new TranslationDAO().exists("id", trId)) {
            throw new BlockedTranslationException(ErrorMsgs.TRANSLATION_NOT_EXISTS);
        }
        if (new TrContextDAO().hasContextFromOtherUser(trId, userId)) {
            throw new BlockedTranslationException(ErrorMsgs.CONTEXTS_ADDED_BY_OTHERS);
        }
        if (new ReportDAO().isReported(trId)) {
            throw new BlockedTranslationException(ErrorMsgs.TRANSLATION_REPORTED);
        }
    }

    /**
     * Limpiamos todos los hijos que queden huérfanos, es decir, que no sean 
     * usados por otras entidades 
     */
    public void deleteIfOrphans(TranslationVO ctr) {
        String wordFrom = ctr.getWordFrom();
        String wordTo = ctr.getWordTo();
        String langFrom = ctr.getLangFrom();
        String langTo = ctr.getLangTo();
        String accepFrom = ctr.getAccepFrom();
        String accepTo = ctr.getAccepTo();

        new WlaDAO().deleteIfOrphan(wordFrom, langFrom, accepFrom);
        new WlaDAO().deleteIfOrphan(wordTo, langTo, accepTo);
        new WordLanguageDAO().deleteIfOrphan(wordFrom, langFrom);
        new WordLanguageDAO().deleteIfOrphan(wordTo, langTo);
        new WordDAO().deleteIfOrphan(wordFrom);
        new WordDAO().deleteIfOrphan(wordTo);
        new LanguageDAO().deleteIfOrphan(langFrom);
        new LanguageDAO().deleteIfOrphan(langTo);

        new AcceptationDAO().deleteIfOrphan(accepFrom, ctr.getTrId());
        new AcceptationDAO().deleteIfOrphan(accepTo, ctr.getTrId());
    }

    public boolean isCreatedBy(int trId, int userId) {
        Translation tr = findById(trId);
        return tr.getUserId() == userId;
    }

    private void loadRecoveryTrLink(HttpServletRequest hsr, TranslationVO ctr) {
//        String link = Common.getFullUrl(hsr);
        hsr.setAttribute("wordFrom", ctr.getWordFrom());
        hsr.setAttribute("wordTo", ctr.getWordTo());
        hsr.setAttribute("langFrom", ctr.getLangFrom());
        hsr.setAttribute("langTo", ctr.getLangTo());
        hsr.setAttribute("accepFrom", ctr.getAccepFrom());
        hsr.setAttribute("accepTo", ctr.getAccepTo());
        hsr.setAttribute("deletedTr", true);
    }

    /**
     * Setea el VO con los creadores de cada componente de la traducción:
     * <ul><li>traducción
     * <li>cada acepción de las 2 posibles
     * <li>cada contexto
     * @param trList
     * @param userId 
     */
    public void setCreated(List<TranslationVO> trList, int userId) {
        for (TranslationVO tr : trList) {
            //para la traducción
            boolean isCreatedByUser = isCreatedByUser_tr(tr.getTrId(), userId) ? true : false;
            tr.setCreatedByUser(isCreatedByUser);

            //para la acepción mostrada
            boolean hasAccep = tr.getAccepFrom() != null ? true : false;
            //antes de todo vemos si hay acepción
            if (hasAccep) {
                Integer accepId = new AcceptationDAO().getId(tr.getAccepFrom());
                UserCreatesTrAccep ucta = new UserCreatesTrAccepDAO().getUcta(
                        tr.getTrId(), accepId, userId);
                if (ucta != null) { //si fue el mismo usuario (hay registro en ucta)
                    AcceptationDataVO accepData = tr.getAccepData();
                    isCreatedByUser = isCreatedByUser_accep(ucta.getId(), userId);
                    accepData.setCreatedByUser(isCreatedByUser);
                    tr.setAccepData(accepData);
                } else { //si se creó por otro usuario
                    AcceptationDataVO accepData = tr.getAccepData();
                    boolean isCreatedByOtherUser = isCreatedByOtherUser_accep(
                            tr.getTrId(), accepId, userId);
                    //si es creada por otro usuario significa que no es creada por el usuario
                    accepData.setCreatedByUser(!isCreatedByOtherUser);
                    tr.setAccepData(accepData);
                }
            }

            //para los contextos
            int trId = tr.getTrId();
            for (TrContextVO trc : tr.getContextList()) {
                isCreatedByUser = isCreatedByUser_context(trId, trc.getId(), userId) ? true : false;
                trc.setCreatedByUser(isCreatedByUser);
            }
        }
    }

    /**
     * @return true si la traducción con la id dada es creada por el usuario con
     * su id también indicada como argumento
     */
    private boolean isCreatedByUser_tr(int trId, int userId) {
        return findUniqueByCriteria(
                Restrictions.eq("id", trId),
                Restrictions.eq("userId", userId)) != null;
    }

    /**
     * @return true si la acepción es creada por el usuario
     */
    private boolean isCreatedByUser_accep(Integer uctaId, int userId) {
        return new UserCreatesTrAccepDAO().findUniqueByCriteria(
                Restrictions.eq("id", uctaId),
                Restrictions.eq("userId", userId)) != null;
    }

    /**
     * @return true si la acepción es creada por otro usuario
     */
    private boolean isCreatedByOtherUser_accep(int trId, int accepId, int userId) {
        return new UserCreatesTrAccepDAO().findUniqueByCriteria(
                Restrictions.eq("translationId", trId),
                Restrictions.eq("acceptationId", accepId),
                Restrictions.ne("userId", userId)) != null;
    }

    /**
     * @return true si el contexto es creado por el usuario
     */
    private boolean isCreatedByUser_context(int cId, int trId, int userId) {
        return new TrContextDAO().findUniqueByCriteria(
                Restrictions.eq("id", cId),
                Restrictions.eq("translationId", trId),
                Restrictions.eq("userId", userId)) != null;
    }
}