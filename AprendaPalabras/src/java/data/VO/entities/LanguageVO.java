/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.Language;

/**
 *
 * @author yomac
 */
public class LanguageVO {
    private Language innerValue;
    private UserVO creator;

    public LanguageVO(Language innerValue) {
        this.innerValue = innerValue;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public Language getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(Language innerValue) {
        this.innerValue = innerValue;
    }

    
    
    
    
}
