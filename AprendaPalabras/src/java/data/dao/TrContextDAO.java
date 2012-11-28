/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.VO.PhraseVO;
import data.VO.TrContextVO;
import data.VO.TranslationVO;
import data.model.Phrase;
import data.model.Translation;
import data.model.TranslationContext;
import form.singleFields.FieldWord;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class TrContextDAO extends GenericHibernateDAO<TranslationContext, Integer> {

    public int createContext(TranslationVO tr, FieldWord phraseFrom,
            FieldWord phraseTo, int userId) {
        return createContext(tr, phraseFrom.getFieldValue(), phraseTo.getFieldValue(), userId);
    }

    public void createBackContexts(TranslationVO tr, Translation newTr) {
        for (TrContextVO trc : tr.getContextList()) {
            createBackContext(tr, trc.getPhraseFrom(), trc.getPhraseTo(), newTr);
        }
    }

    public int createContext(TranslationVO tr, String phraseFrom,
            String phraseTo, int userId) {

        String ph1 = tr.isSwapped() ? phraseTo : phraseFrom;
        String ph2 = tr.isSwapped() ? phraseFrom : phraseTo;
        
        Translation t = new TranslationDAO().findById(tr.getTrId());
        int wla1id = t.getWla1Id();
        int wla2id = t.getWla2Id();
        
        int phraseFrom_id = new PhraseDAO().createPhrase(ph1, wla1id);
        int phraseTo_id = new PhraseDAO().createPhrase(ph2, wla2id);

        if (exists(phraseFrom_id, phraseTo_id, tr.getTrId())) {
            return getId(phraseFrom_id, phraseTo_id, tr.getTrId());
        }

        TranslationContext trc = new TranslationContext(new Date(), phraseFrom_id,
                phraseTo_id, userId, tr.getTrId());
        return makePersistent(trc).getId();
    }

    public int createBackContext(TranslationVO tr, String phraseFrom,
            String phraseTo, Translation newTr) {
        //sacamos las id's de las wl
        int wl1Id = new WordLanguageDAO().getId(tr.getWordFrom(), tr.getLangFrom());
        int wl2Id = new WordLanguageDAO().getId(tr.getWordTo(), tr.getLangTo());

        //creamos cada frase y la añadimos al contexto
        int wlaFrom_id = new WlaDAO().getId(wl1Id, tr.getAccepFrom());
        int wlaTo_id = new WlaDAO().getId(wl2Id, tr.getAccepTo());
        int phraseFrom_id = new PhraseDAO().createPhrase(phraseFrom, wlaFrom_id);
        int phraseTo_id = new PhraseDAO().createPhrase(phraseTo, wlaTo_id);

        if (exists(phraseFrom_id, phraseTo_id, tr.getTrId())) {
            return getId(phraseFrom_id, phraseTo_id, tr.getTrId());
        }

        //tenemos que buscar la nueva traducción ya que el id de la anterior es distinto
        TranslationContext trc = new TranslationContext(new Date(), phraseFrom_id,
                phraseTo_id, newTr.getUserId(), newTr.getId());
        return makePersistent(trc).getId();
    }

    public void updateContext(TranslationVO tr, int cId, FieldWord phraseFrom,
            FieldWord phraseTo) {

        TrContextVO trcVo = null;
        for (TrContextVO c : tr.getContextList()) {
            if (c.getId() == cId) {
                trcVo = c;
                break;
            }
        }

        TranslationContext trc = findById(cId);

        //frases registradas anteriormente
        Phrase ph1_r = new PhraseDAO().findById(trc.getPhrase1id());
        Phrase ph2_r = new PhraseDAO().findById(trc.getPhrase2id());

        //frases nuevas introd. en el formulario
        String phFrom = phraseFrom.getFieldValue();
        String phTo = phraseTo.getFieldValue();
        String ph1_f = trcVo.isSwapped() ? phTo : phFrom;
        String ph2_f = trcVo.isSwapped() ? phFrom : phTo;

        //se actualizan las frases si han cambiado
        if (!(ph1_f.equalsIgnoreCase(ph1_r.getPhrase()))) {
            int ph1Id = new PhraseDAO().createPhrase(ph1_f, ph1_r.getWlaId());
            trc.setPhrase1id(ph1Id);
            makePersistent(trc);
            new PhraseDAO().deleteIfOrphan(ph1_r);
        }
        if (!(ph2_f.equalsIgnoreCase(ph2_r.getPhrase()))) {
            int ph2Id = new PhraseDAO().createPhrase(ph2_f, ph2_r.getWlaId());
            trc.setPhrase2id(ph2Id);
            makePersistent(trc);
            new PhraseDAO().deleteIfOrphan(ph2_r);
        }
    }

    public boolean exists(int phFromId, int phToId, int trId) {
        Criterion c1 = Restrictions.eq("phrase1id", phFromId);
        Criterion c2 = Restrictions.eq("phrase2id", phToId);
        Criterion c3 = Restrictions.eq("translationId", trId);
        return findUniqueByCriteria(c1, c2, c3) != null;
    }

    private int getId(int phFromId, int phToId, int trId) {
        Criterion c1 = Restrictions.eq("phrase1id", phFromId);
        Criterion c2 = Restrictions.eq("phrase2id", phToId);
        Criterion c3 = Restrictions.eq("translationId", trId);
        return findUniqueByCriteria(c1, c2, c3).getId();
    }

    List<TrContextVO> getTrContextList(int trId) {
        String sql = "SELECT trc.id, ph1.phrase, l1.language, ph2.phrase, l2.language, "
                + "trc.creationDate, u.username, u.id "
                + ""
                + "FROM TranslationContext as trc "
                + ""
                + "JOIN Phrase as ph1 ON ph1.id = trc.Phrase1id "
                + "JOIN Phrase as ph2 ON ph2.id = trc.Phrase2id "
                + "JOIN User as u ON u.id = trc.User_id "
                + ""
                + "JOIN WLA as wla1 ON ph1.WLA_id = wla1.id "
                + "JOIN WordLanguage as wl1 ON wla1.WordLanguage_id = wl1.id "
                + "JOIN Language as l1 ON wl1.Language_id = l1.id "
                + ""
                + "JOIN WLA as wla2 ON ph2.WLA_id = wla2.id "
                + "JOIN WordLanguage as wl2 ON wla2.WordLanguage_id = wl2.id "
                + "JOIN Language as l2 ON wl2.Language_id = l2.id "
                //                + "JOIN Translation as tr ON tr.id = Translation_id "
                + ""
                + "WHERE trc.Translation_id = :trId";

        Query query = getSession().createSQLQuery(sql).
                addScalar("trc.id", Hibernate.INTEGER).
                addScalar("ph1.phrase", Hibernate.STRING).
                addScalar("l1.language", Hibernate.STRING).
                addScalar("ph2.phrase", Hibernate.STRING).
                addScalar("l2.language", Hibernate.STRING).
                addScalar("trc.creationDate", Hibernate.TIMESTAMP).
                addScalar("u.username", Hibernate.STRING).
                addScalar("u.id", Hibernate.INTEGER).
                setParameter("trId", trId);

        List l = query.list();
        session.close();

        List<TrContextVO> cList = new ArrayList<TrContextVO>();

        Iterator it = l.iterator();
        while (it.hasNext()) {
            Object[] r = (Object[]) it.next();
            int id = (Integer) r[0];
            String ph1 = (String) r[1];
            String l1 = (String) r[2];
            String ph2 = (String) r[3];
            String l2 = (String) r[4];
            Date creationDate = (Date) r[5];
            String userCreatorName = (String) r[6];
            int userCreatorId = (Integer) r[7];

            TrContextVO tr = new TrContextVO(id, ph1, l1, ph2, l2, creationDate,
                    userCreatorName, userCreatorId);

            cList.add(tr);
        }
        return cList;
    }

    public List<TrContextVO> getTrContextList_withPhraseVO(int trId) {
        String sql = "SELECT trc.id, "
                + "{ph1.*}, l1.language, {ph2.*}, l2.language, "
                + "trc.creationDate, u.username, u.id "
                + ""
                + "FROM TranslationContext as trc "
                + ""
                + "JOIN Phrase as ph1 ON ph1.id = trc.Phrase1id "
                + "JOIN Phrase as ph2 ON ph2.id = trc.Phrase2id "
                + "JOIN User as u ON u.id = trc.User_id "
                + ""
                + "JOIN WLA as wla1 ON ph1.WLA_id = wla1.id "
                + "JOIN WordLanguage as wl1 ON wla1.WordLanguage_id = wl1.id "
                + "JOIN Language as l1 ON wl1.Language_id = l1.id "
                + ""
                + "JOIN WLA as wla2 ON ph2.WLA_id = wla2.id "
                + "JOIN WordLanguage as wl2 ON wla2.WordLanguage_id = wl2.id "
                + "JOIN Language as l2 ON wl2.Language_id = l2.id "
                //                + "JOIN Translation as tr ON tr.id = Translation_id "
                + ""
                + "WHERE trc.Translation_id = :trId";

        Query query = getSession().createSQLQuery(sql).
                addScalar("trc.id", Hibernate.INTEGER).
                addEntity("ph1", Phrase.class).
                addScalar("l1.language", Hibernate.STRING).
                addEntity("ph2", Phrase.class).
                addScalar("l2.language", Hibernate.STRING).
                addScalar("trc.creationDate", Hibernate.TIMESTAMP).
                addScalar("u.username", Hibernate.STRING).
                addScalar("u.id", Hibernate.INTEGER).
                setParameter("trId", trId);

        List l = query.list();
        session.close();

        List<TrContextVO> cList = new ArrayList<TrContextVO>();

        Iterator it = l.iterator();
        while (it.hasNext()) {
            Object[] r = (Object[]) it.next();
            int id = (Integer) r[0];
            Phrase ph1 = (Phrase) r[1];
            String l1 = (String) r[2];
            Phrase ph2 = (Phrase) r[3];
            String l2 = (String) r[4];
            Date creationDate = (Date) r[5];
            String userCreatorName = (String) r[6];
            int userCreatorId = (Integer) r[7];

            PhraseVO phVo1 = new PhraseVO(ph1.getId(), ph1.getPhrase(),
                    ph1.getWlaId(), l1);
            PhraseVO phVo2 = new PhraseVO(ph2.getId(), ph2.getPhrase(),
                    ph2.getWlaId(), l2);

            TrContextVO tr = new TrContextVO(id, phVo1, phVo2, creationDate,
                    userCreatorName, userCreatorId);

            cList.add(tr);
        }
        return cList;
    }

    public void deleteContexts(int trId) {
        List<TranslationContext> cList =
                findByCriteria(Restrictions.eq("translationId", trId));

        for (TranslationContext trc : cList) {
            /** eliminamos los reportes para cada contexto */
            new ReportDAO().deleteReports_forContext(trc.getId());
            /** eliminamos cada contexto */
            TranslationContext t = findById(trc.getId());
            makeTransient(t);
            /** eliminamos las frases de cada contexto */
            Phrase ph1 = new PhraseDAO().findById(trc.getPhrase1id());
            Phrase ph2 = new PhraseDAO().findById(trc.getPhrase2id());
            new PhraseDAO().makeTransient(ph1);
            new PhraseDAO().makeTransient(ph2);
        }
    }

    /**
     * indica si la traducción tiene contextos creados por otros usuarios
     * @param trId
     * @param userId
     * @return 
     */
    public boolean hasContextFromOtherUser(int trId, int userId) {
        Criterion c1 = Restrictions.eq("translationId", trId);
        Criterion c2 = Restrictions.ne("userId", userId);
        return !findByCriteria(c1, c2).isEmpty();
    }

    /**
     * Elimina un contexto y su par de frases dado su id
     * @param cId 
     */
    public void deleteContext(int cId) {
        TranslationContext trc = findById(cId);
        makeTransient(trc);
        new PhraseDAO().deletePhrases(trc);
    }
    /**
     * actualiza las frases del contexto de una traducción
     * @param cId
     * @param phraseFrom
     * @param phraseTo
     * @param userId 
     */
//    public void updateContext(TranslationVO tr, Integer cId, FieldWord phraseFrom, FieldWord phraseTo) {
//        for(TrContextVO trc : tr.getContextList()){
//            if(trc.getId() == cId){
//                PhraseVO phFromVo = trc.getPhraseFrom();
//                Phrase phFrom = new PhraseDAO().findById(phFromVo.getId());
//                
//                /**
//                 * 
//                 * 
//                 * 
//                 * 
//                 * falta por implementar
//                 */
//            }
//        }
//    }
}