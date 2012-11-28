/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.UserHasTranslation;

/**
 * Elemento de la lista "myTranslations"
 * @author yomac
 */
public class UserHasTrVO {
    
    private UserHasTranslation innerValue;
    private UserVO user;
    private TranslationVO translation;

    public UserHasTrVO(UserHasTranslation innerValue) {
        this.innerValue = innerValue;
    }

    public UserHasTranslation getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(UserHasTranslation innerValue) {
        this.innerValue = innerValue;
    }

    public TranslationVO getTranslation() {
        return translation;
    }

    public void setTranslation(TranslationVO translation) {
        this.translation = translation;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    

    

    
    
    
}