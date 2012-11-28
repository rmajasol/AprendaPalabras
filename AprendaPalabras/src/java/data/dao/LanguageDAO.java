/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import java.util.ArrayList;
import java.util.List;
import data.model.Language;
import java.util.Date;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class LanguageDAO extends GenericHibernateDAO<Language, Integer> {

    public int getId(String lang) {
        return findUniqueByCriteria(
                Restrictions.eq("language", lang).ignoreCase()).getId();
    }

    /**
     * elimina un idioma si no es nativo de ningún usuario o bien ninguna palabra
     * le pertenece
     */
    public void deleteIfOrphan(String lang) {
        int langId = getId(lang);
        boolean hasParentUser = new UserDAO().hasChildLang(langId);
        boolean hasParentWordLanguage = new WordLanguageDAO().exists("languageId", langId);
        if(!hasParentUser && !hasParentWordLanguage){
            Language l = findById(langId);
            makeTransient(l);
        }
    }

    public int createLanguageIfNotExists(String lang, int userId) {
        if (!exists(lang)) {
            Language l = new Language(lang, new Date(), userId);
            return makePersistent(l).getId();
        }
        return getId(lang);
    }
    
    

    public List<String> listAllLanguagesOrdered() {
        List<Language> langList = findAllOrdered(Order.asc("language"));
        ArrayList<String> langs = new ArrayList<String>();
        for (Language lang : langList) {
            langs.add(lang.getLanguage());
        }
        return langs;
    }

    public int createLanguage(String lr, String lf, int userId) {
        if(lr.equalsIgnoreCase(lf)){
            return getId(lr);
        }
        deleteIfOrphan(lr);
        return createLanguageIfNotExists(lf, userId);
    }

    private boolean exists(String lf) {
        return exists("language", lf);
    }
}
