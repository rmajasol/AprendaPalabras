/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import exceptions.UserNotFoundException;
import data.GenericHibernateDAO;
import data.model.AccessKey;
import data.model.User;
import error.ErrorMsgs;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class UserDAO extends GenericHibernateDAO<User, Integer> {

    public User getUser(AccessKey ak) throws Exception {
        User user = new UserDAO().findById(ak.getUserId());
        if (user == null) {
            throw new UserNotFoundException(ErrorMsgs.USER_NOT_FOUND);
        }
        return user;
    }

    public boolean isUnsuscribed() {
        return true;
    }

    /**
     * mira si hay algún usuario activo usando el lenguaje
     * @param langId
     * @return 
     */
    public boolean hasChildLang(int langId) {
        Criterion c1 = Restrictions.eq("languageId", langId);
        Criterion c2 = Restrictions.eq("defaultLangFrom", langId);
        Criterion c3 = Restrictions.eq("defaultLangTo", langId);
        Criterion co1 = Restrictions.or(c1, c2);
        Criterion co2 = Restrictions.or(co1, c3);
        Criterion crit = Restrictions.and(co2, Restrictions.isNotNull("username"));
        return !findByCriteria(crit).isEmpty();
    }

    /**
     * Una vez el usuario dado de baja se borran todos sus atributos
     * @param user 
     */
    void clearUser(User user) {
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setRegistrationDate(null);
        user.setIpAddressReg(null);
        user.setValidated(null);
        
        String userNativeLang = new LanguageDAO().findById(user.getLanguageId()).getLanguage();
        user.setLanguageId(null);
        new UserDAO().makePersistent(user);
        new LanguageDAO().deleteIfOrphan(userNativeLang);

        user.setRoleId(null);

        if (user.getDefaultLangFrom() != null) {
            String userDefLangFrom = new LanguageDAO().findById(user.getDefaultLangFrom()).getLanguage();
            user.setDefaultLangFrom(null);
            new UserDAO().makePersistent(user);
            new LanguageDAO().deleteIfOrphan(userDefLangFrom);
        }
        if (user.getDefaultLangTo() != null) {
            String userDefLangTo = new LanguageDAO().findById(user.getDefaultLangTo()).getLanguage();
            user.setDefaultLangFrom(null);
            new UserDAO().makePersistent(user);
            new LanguageDAO().deleteIfOrphan(userDefLangTo);
        }

        user.setInvertAcceptations(null);
        user.setHideDefLangFrom(null);
        user.setHideDefLangTo(null);
        user.setAutoAdding(null);
        new UserDAO().makePersistent(user);
    }
}
