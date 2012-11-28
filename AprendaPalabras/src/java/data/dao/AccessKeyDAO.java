/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import exceptions.InvalidKeyException;
import data.GenericHibernateDAO;
import data.model.AccessKey;
import data.model.User;
import org.hibernate.criterion.Restrictions;
import utils.Common;
import error.ErrorMsgs;

/**
 *
 * @author yomac
 */
public class AccessKeyDAO extends GenericHibernateDAO<AccessKey, Integer> {
    
    public void generateAccessKey(User user){
        AccessKey ak = new AccessKey();
        int userId = new UserDAO().findUniqueByExample(user).getId();
        ak.setUserId(userId);
        String accessKey = Common.generateKey();
        ak.setAccessKey(accessKey);
        makePersistent(ak);
    }
    
    public AccessKey getAccessKey(String key) throws Exception {
        //busco si está esa clave del link en la BD y saco el usuario que la tiene
        AccessKey ak = new AccessKeyDAO().findUniqueByCriteria(
                Restrictions.eq("accessKey", key));
        if (ak == null) {
            throw new InvalidKeyException(ErrorMsgs.INVALID_ACCESS_KEY);
        }
        return ak;
    }


}
