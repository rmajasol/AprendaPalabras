/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.Wla;

/**
 *
 * @author yomac
 */
public class WlaVO {

    private Wla innerValue;
    private WordLanguageVO wordLanguage;
    private AcceptationVO acceptation;
    private UserVO creator;

    public WlaVO(WordLanguageVO wordLanguage, AcceptationVO acceptation) {
        this.wordLanguage = wordLanguage;
        this.acceptation = acceptation;
    }

    public AcceptationVO getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(AcceptationVO acceptation) {
        this.acceptation = acceptation;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public Wla getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(Wla innerValue) {
        this.innerValue = innerValue;
    }

    public WordLanguageVO getWordLanguage() {
        return wordLanguage;
    }

    public void setWordLanguage(WordLanguageVO wordLanguage) {
        this.wordLanguage = wordLanguage;
    }
    
    
    
    
}
