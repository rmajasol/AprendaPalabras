/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO;

/**
 *
 * @author yomac
 */
public class PhraseVO {
    private int id;
    private String phrase;
    private int wlaId;
    private String lang;

    public PhraseVO(int id, String phrase, int wlaId, String lang) {
        this.id = id;
        this.phrase = phrase;
        this.wlaId = wlaId;
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public int getWlaId() {
        return wlaId;
    }

    public void setWlaId(int wlaId) {
        this.wlaId = wlaId;
    }

    
    
}
