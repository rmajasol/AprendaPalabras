/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO.entities;

/**
 *
 * @author yomac
 */
public class TrContextVO {

    String phrase1;
    String phrase2;
    String creator;
    String creationDate;

    public TrContextVO(String phrase1, String phrase2, String creator, String creationDate) {
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
        this.creator = creator;
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getPhrase1() {
        return phrase1;
    }

    public String getPhrase2() {
        return phrase2;
    }
}
