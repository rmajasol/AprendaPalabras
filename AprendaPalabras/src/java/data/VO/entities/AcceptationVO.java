/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.Acceptation;

/**
 *
 * @author yomac
 */
public class AcceptationVO {

    private Acceptation innerValue;
    private UserVO creator;

    public AcceptationVO(Acceptation innerValue) {
        this.innerValue = innerValue;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public Acceptation getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(Acceptation innerValue) {
        this.innerValue = innerValue;
    }
    
    
    
}
