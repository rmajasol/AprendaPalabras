/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.model.Phrase;
import data.model.Translation;
import data.model.TranslationContext;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class PhraseDAO extends GenericHibernateDAO<Phrase, Integer> {

    public int createPhrase(String phrase, int wlaId) {
        if (exists(phrase, wlaId)) {
            return getId(phrase, wlaId);
        }
        Phrase ph = new Phrase(phrase, wlaId);
        return makePersistent(ph).getId();
    }

    public boolean exists(String phrase, int wlaId) {
        Criterion c1 = Restrictions.eq("phrase", phrase).ignoreCase();
        Criterion c2 = Restrictions.eq("wlaId", wlaId);
        return findUniqueByCriteria(c1, c2) != null;
    }

    private int getId(String phrase, int wlaId) {
        Criterion c1 = Restrictions.eq("phrase", phrase).ignoreCase();
        Criterion c2 = Restrictions.eq("wlaId", wlaId);
        return findUniqueByCriteria(c1, c2).getId();
    }

    public Phrase getPhrase(String phrase, int cId) {
        String sql =
                "SELECT ph.* FROM Phrase as ph "
                + ""
                + "JOIN TranslationContext as tc ON tc.Phrase1id = ph.id OR tc.Phrase2id = ph.id "
                + "JOIN Translation as tr ON tr.id = tc.Translation_id "
                + ""
                + "WHERE tc.id = :cId "
                + "AND (tr.WLA1_id = ph.WLA_id OR tr.WLA2id = ph.WLA_id)";

        Query query = getSession().createSQLQuery(sql).
                addEntity(Phrase.class).
                setParameter("cId", cId);
        Phrase ph = (Phrase) query.uniqueResult();
        session.close();
        return ph;
    }

    public void updateToNewWla(int old_wla_id, int new_wla_id) {
        List<Phrase> phList = findByCriteria(
                Restrictions.eq("wlaId", old_wla_id));
        for (Phrase ph : phList) {
            if (ph.getWlaId() != new_wla_id) {
                ph.setWlaId(new_wla_id);
                makePersistent(ph);
            }
        }
    }

    public void deletePhrases(TranslationContext trc) {
        Phrase ph1 = new PhraseDAO().findById(trc.getPhrase1id());
        if (!isInAnotherContext(ph1, trc.getId())) {
            makeTransient(ph1);
        }
        Phrase ph2 = new PhraseDAO().findById(trc.getPhrase2id());
        if (!isInAnotherContext(ph2, trc.getId())) {
            makeTransient(ph2);
        }
    }

    public boolean isInAnotherContext(Phrase ph, int cId) {
        Criterion c1 = Restrictions.eq("phrase1id", ph.getId());
        Criterion c2 = Restrictions.eq("phrase2id", ph.getId());
        Criterion ca = Restrictions.or(c1, c2);
        Criterion cb = Restrictions.ne("id", cId);
        return !new TrContextDAO().findByCriteria(ca, cb).isEmpty();
    }

    /**
     * se crea la frase ph1_f y si no hay otro contexto que use la frase ph1_r esta se elimina.
     */
    public void updatePhrase(Phrase ph_r, String ph_f) {
    }

    /**
     * si no hay otro contexto que use la frase se elimina
     * @param ph_r 
     */
    public void deleteIfOrphan(Phrase ph) {
        Criterion c1 = Restrictions.eq("phrase1id", ph.getId());
        Criterion c2 = Restrictions.eq("phrase2id", ph.getId());
        Criterion c = Restrictions.or(c1, c2);
        boolean hasParentTrContext = !new TrContextDAO().findByCriteria(c).isEmpty();
        if (!hasParentTrContext) {
            makeTransient(ph);
        }
    }
}