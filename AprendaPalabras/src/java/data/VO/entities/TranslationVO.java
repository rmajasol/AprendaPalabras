package data.VO.entities;

import data.model.Translation;

/**
 * Elemento de la lista "myContributions"
 * @author yomac
 */
public class TranslationVO {

    private Translation innerValue;
    private UserVO creator;
    private WlaVO wlaFrom;
    private WlaVO wlaTo;
    private boolean added; //si está añadida a mi lista personal

    public TranslationVO(Translation innerValue) {
        this.innerValue = innerValue;
    }
    
    

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public Translation getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(Translation innerValue) {
        this.innerValue = innerValue;
    }

    public WlaVO getWlaFrom() {
        return wlaFrom;
    }

    public void setWlaFrom(WlaVO wlaFrom) {
        this.wlaFrom = wlaFrom;
    }

    public WlaVO getWlaTo() {
        return wlaTo;
    }

    public void setWlaTo(WlaVO wlaTo) {
        this.wlaTo = wlaTo;
    }

    
    

    
    
}