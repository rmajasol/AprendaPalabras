package data.model;
// Generated 12-jun-2012 14:23:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Word generated by hbm2java
 */
public class Word  implements java.io.Serializable {


     private Integer id;
     private String word;
     private Date creationDate;
     private int userId;

    public Word() {
    }

    public Word(String word, Date creationDate, int userId) {
       this.word = word;
       this.creationDate = creationDate;
       this.userId = userId;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getWord() {
        return this.word;
    }
    
    public void setWord(String word) {
        this.word = word;
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


