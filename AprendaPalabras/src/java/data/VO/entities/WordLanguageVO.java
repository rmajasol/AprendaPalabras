/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

import data.model.WordLanguage;

/**
 *
 * @author yomac
 */
public class WordLanguageVO {

    private WordLanguage innerValue;
    private WordVO word;
    private LanguageVO language;
    private UserVO creator;

    public WordLanguageVO(WordVO word, LanguageVO language) {
        this.word = word;
        this.language = language;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public WordLanguage getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(WordLanguage innerValue) {
        this.innerValue = innerValue;
    }

    public LanguageVO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageVO language) {
        this.language = language;
    }

    public WordVO getWord() {
        return word;
    }

    public void setWord(WordVO word) {
        this.word = word;
    }

    
    
    
}