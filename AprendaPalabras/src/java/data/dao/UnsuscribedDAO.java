/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.model.User;
import data.model.Unsuscribed;
import java.util.Date;

/**
 *
 * @author yomac
 */
public class UnsuscribedDAO extends GenericHibernateDAO<Unsuscribed, Integer> {

    public void unsuscribe(User user, String feedback) {
        if (!exists("userId", user.getId())) {
            Unsuscribed unsuscr = new Unsuscribed(user.getId(), feedback, false, new Date());
            makePersistent(unsuscr);
        }
        new UserDAO().clearUser(user);
        new UserHasTranslationDAO().clearAddedTranslations(user);
    }
}
