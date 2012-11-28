/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.model.UserCreatesTrAccep;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class UserCreatesTrAccepDAO
        extends GenericHibernateDAO<UserCreatesTrAccep, Integer> {

    public UserCreatesTrAccep find(int trId, int accepId) {
        return findUniqueByCriteria(
                Restrictions.eq("translationId", trId),
                Restrictions.eq("acceptationId", accepId));
    }
    
    /**
     * devuelve los dos ucta posibles dada la id de la traducción
     * @param trId
     * @return 
     */
    public List<UserCreatesTrAccep> find(int trId) {
        return findByCriteria(
                Restrictions.eq("translationId", trId));
    }

    public boolean exists(int trId, int accepId) {
        return findUniqueByCriteria(
                Restrictions.eq("translationId", trId),
                Restrictions.eq("acceptationId", accepId)) != null;
    }

    public void createEntry(int trId, int accepId, int userId) {
        if (!exists(trId, accepId, userId)) {
            UserCreatesTrAccep u = new UserCreatesTrAccep(trId, accepId, userId, new Date());
            new UserCreatesTrAccepDAO().makePersistent(u);
        }
    }

    public UserCreatesTrAccep getUcta(int trId, int accepId, int userId) {
        return findUniqueByCriteria(
                Restrictions.eq("translationId", trId),
                Restrictions.eq("acceptationId", accepId),
                Restrictions.eq("userId", userId));
    }
    
    public boolean exists(int trId, int accepId, int userId){
        return getUcta(trId, accepId, userId) != null;
    }

    public void deleteIfOrphan(int trId, String accepFrom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void deleteUctas(int trId) {
        List<UserCreatesTrAccep> list = find(trId);
        for(UserCreatesTrAccep u : list){
            /** antes de eliminar cada ucta eliminamos todos sus reportes */
            new ReportDAO().deleteReports_forUcta(u.getId());
            /** eliminamos el ucta */
            makeTransient(u);
        }
    }
}