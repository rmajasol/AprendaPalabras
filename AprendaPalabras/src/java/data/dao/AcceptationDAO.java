/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.model.Acceptation;
import data.model.Report;
import data.model.UserCreatesTrAccep;
import java.util.Date;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class AcceptationDAO extends GenericHibernateDAO<Acceptation, Integer> {

    public int getId(String acceptation) {
        return findUniqueByCriteria(
                Restrictions.eq("acceptation", acceptation).ignoreCase()).getId();
    }

    public boolean exists(String acceptation) {
        return new AcceptationDAO().exists("acceptation", acceptation);
    }

    public Acceptation findUnique(String acceptation) {
        return findUniqueByCriteria(Restrictions.eq("acceptation", acceptation).ignoreCase());
    }

    /**
     * crea la acepción si no está y busca su id
     * @param wordId
     * @param langId
     * @param userId
     * @return 
     */
    public int find(String acceptation, int userId) {
        Acceptation a2 = findUnique(acceptation);
        if (a2 == null) {
            Acceptation a = new Acceptation(acceptation, new Date(), userId);
            return makePersistent(a).getId();
        }
        return a2.getId();
    }

    public Acceptation createAcceptation(String acceptation, int userId) {
        if (!exists(acceptation)) {
            Acceptation a = new Acceptation(acceptation, new Date(), userId);
            return makePersistent(a);
        }
//        return getId(acceptation); 
        return findUnique(acceptation);
    }

    public Acceptation createAcceptation(String ar, String af, int userId) {
        if (af.isEmpty()) {
            return null;
        } else {
            return createAcceptation(af, userId);
        }
    }

    /**
     * elimina una acepción si no pertenece a ningún wla
     */
    public void deleteIfOrphan(String acc, int trId) {
        if (acc == null || acc.isEmpty()) {
            return;
        }
        int accepId = getId(acc);
        boolean hasParentWla = new WlaDAO().exists("acceptationId", accepId);
        if (!hasParentWla) {
            /** eliminamos reportes relativos al ucta de la acepción si lo tiene */
            if (new UserCreatesTrAccepDAO().find(trId, accepId) != null) {
                Integer uctaId = new UserCreatesTrAccepDAO().find(trId, accepId).getId();
                new ReportDAO().deleteReports_forUcta(uctaId);
                /** eliminamos el ucta de la acepción si lo tiene */
                UserCreatesTrAccep u = new UserCreatesTrAccepDAO().findById(uctaId);
                new UserCreatesTrAccepDAO().makeTransient(u);
            }
            /** eliminamos la acepción */
            Acceptation a = findById(accepId);
            makeTransient(a);
        }
    }
}
