package data.model;
// Generated 12-jun-2012 14:23:10 by Hibernate Tools 3.2.1.GA



/**
 * Phrase generated by hbm2java
 */
public class Phrase  implements java.io.Serializable {


     private Integer id;
     private String phrase;
     private int wlaId;

    public Phrase() {
    }

    public Phrase(String phrase, int wlaId) {
       this.phrase = phrase;
       this.wlaId = wlaId;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPhrase() {
        return this.phrase;
    }
    
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public int getWlaId() {
        return this.wlaId;
    }
    
    public void setWlaId(int wlaId) {
        this.wlaId = wlaId;
    }




}

