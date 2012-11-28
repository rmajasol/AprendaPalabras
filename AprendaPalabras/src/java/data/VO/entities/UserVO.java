/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.User;
import java.util.Date;

/**
 *
 * @author yomac
 */
public class UserVO {

    private User user;
    private LanguageVO language;
    private RoleVO roleId;
    private LanguageVO defaultLangFrom;
    private LanguageVO defaultLangTo;

    public UserVO(User user) {
        this.user = user;
    }

    
}
