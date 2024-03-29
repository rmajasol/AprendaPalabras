package data.model;
// Generated 12-jun-2012 14:23:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * WordLanguage generated by hbm2java
 */
public class WordLanguage  implements java.io.Serializable {


     private Integer id;
     private int wordId;
     private int languageId;
     private Date creationDate;
     private int userId;

    public WordLanguage() {
    }

    public WordLanguage(int wordId, int languageId, Date creationDate, int userId) {
       this.wordId = wordId;
       this.languageId = languageId;
       this.creationDate = creationDate;
       this.userId = userId;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getWordId() {
        return this.wordId;
    }
    
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
    public int getLanguageId() {
        return this.languageId;
    }
    
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }




}


