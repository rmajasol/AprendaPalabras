/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.model.Acceptation;
import data.model.Wla;
import java.util.Date;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class WlaDAO extends GenericHibernateDAO<Wla, Integer> {

    /**
     * 
     * @param wlId
     * @param acceptation
     * @param userId
     * @return 
     */
    public int createIfNotExists(int wlId, String acceptation, int userId) {
        Criterion c1 = Restrictions.eq("wordLanguageId", wlId);
        Criterion c2;
        int acceptId = 0;
        if (acceptation == null || acceptation.isEmpty()) {
            c2 = Restrictions.isNull("acceptationId");
        } else {
            acceptId = new AcceptationDAO().find(acceptation, userId);
            c2 = Restrictions.eq("acceptationId", acceptId);
        }
        Wla wla = findUniqueByCriteria(c1, c2);
        //si la wla no está se crea
        if (wla == null) {
            Wla wla2 = (acceptation == null || acceptation.isEmpty())
                    ? new Wla(wlId, new Date(), userId)
                    : new Wla(wlId, acceptId, new Date(), userId);
            return makePersistent(wla2).getId();
        }
        return wla.getId();
    }

    public int createWlaIfNotExists(String lr, String lf, String wr, String wf, String ar,
            String af, int userId) {
        int wlId = new WordLanguageDAO().createWordLanguage(lr, lf, wr, wf, userId);
        Acceptation a = new AcceptationDAO().createAcceptation(ar, af, userId);
        if (a == null) {
            return create(wlId, userId);
        }
        return create(wlId, a.getId(), userId);
    }

    private int create(int wlId, int accepId, int userId) {
        if (!exists(wlId, accepId)) {
            Wla wla = new Wla(wlId, accepId, new Date(), userId);
            return makePersistent(wla).getId();
        }
        return getId(wlId, accepId);
    }

    private int create(int wlId, int userId) {
        if (!exists(wlId)) {
            Wla wla = new Wla(wlId, new Date(), userId);
            return makePersistent(wla).getId();
        }
        return getId(wlId);
    }

    private boolean exists(int wlId, int accepId) {
        Criterion c1 = Restrictions.eq("wordLanguageId", wlId);
        Criterion c2 = Restrictions.eq("acceptationId", accepId);
        return findUniqueByCriteria(c1, c2) != null;
    }

    private boolean exists(int wlId) {
        Criterion c1 = Restrictions.eq("wordLanguageId", wlId);
        Criterion c2 = Restrictions.isNull("acceptationId");
        return findUniqueByCriteria(c1, c2) != null;
    }

    private int getId(int wlId, int accepId) {
        Criterion c1 = Restrictions.eq("wordLanguageId", wlId);
        Criterion c2 = Restrictions.eq("acceptationId", accepId);
        return findUniqueByCriteria(c1, c2).getId();
    }

    private int getId(int wlId) {
        Criterion c1 = Restrictions.eq("wordLanguageId", wlId);
        Criterion c2 = Restrictions.isNull("acceptationId");
        return findUniqueByCriteria(c1, c2).getId();
    }

    public void deleteIfOrphan(String word, String language, String acceptation) {
        int wlId = new WordLanguageDAO().getId(word, language);
        int wlaId;
        if (acceptation == null) {
            wlaId = getId(wlId);
        } else {
            int accepId = new AcceptationDAO().getId(acceptation);
            wlaId = getId(wlId, accepId);
        }
        Criterion c1 = Restrictions.eq("wla1Id", wlaId);
        Criterion c2 = Restrictions.eq("wla2Id", wlaId);
        Criterion c = Restrictions.or(c1, c2);
        boolean hasParentTranslation = !new TranslationDAO().findByCriteria(c).isEmpty();
        boolean hasParentPhrase = new PhraseDAO().exists("wlaId", wlaId);
        if (!hasParentTranslation && !hasParentPhrase) {
            Wla wla = findById(wlaId);
            makeTransient(wla);
        }
    }

//    public int getId(String word, String lang, String accep) {
//        
//        
//        String sql =
//                "SELECT wla.id "
//                + "FROM WLA "
//                + ""
//                + "JOIN Acceptation as acc ON acc.id = wla.Acceptation_id "
//                + "JOIN WordLanguage as wl ON wl.id = wla.WordLanguage_id "
//                + "JOIN Word as w ON w.id = wl.Word_id "
//                + "JOIN Language as l ON l.id = wl.Language_id "
//                + ""
//                + "WHERE w.word = :word AND l.language = :lang AND acc.acceptation = :accep";
//        Query query = getSession().createSQLQuery(sql).
//                addScalar("wla.id", Hibernate.INTEGER).
//                setParameter("word", word).
//                setParameter("lang", lang).
//                setParameter("accep", accep);
//        Object o = query.uniqueResult();
//        return (Integer) query.uniqueResult();
//    }
    
    
    public int getId(int wlId, String accep) {
        Criterion c1 = Restrictions.eq("wordLanguageId", wlId);
        Criterion c2;
        int acceptId = 0;
        if (accep == null || accep.isEmpty()) {
            c2 = Restrictions.isNull("acceptationId");
        } else {
            acceptId = new AcceptationDAO().getId(accep);
            c2 = Restrictions.eq("acceptationId", acceptId);
        }
        return findUniqueByCriteria(c1, c2).getId();
    }
    
    public int getId(String word, String language, String accep){
        int wlId = new WordLanguageDAO().getId(word, language);
        return getId(wlId, accep);
    }
}
