/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.Word;

/**
 *
 * @author yomac
 */
public class WordVO {
    Word innerValue;
    UserVO creator;

    public WordVO(Word innerValue) {
        this.innerValue = innerValue;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public Word getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(Word innerValue) {
        this.innerValue = innerValue;
    }
    
    

    
    
    
}

