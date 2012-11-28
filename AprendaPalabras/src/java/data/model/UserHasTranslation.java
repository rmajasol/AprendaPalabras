package data.model;
// Generated 12-jun-2012 14:23:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * UserHasTranslation generated by hbm2java
 */
public class UserHasTranslation  implements java.io.Serializable {


     private Integer id;
     private int userId;
     private int translationId;
     private Date dateAdded;
     private boolean learned;

    public UserHasTranslation() {
    }

    public UserHasTranslation(int userId, int translationId, Date dateAdded, boolean learned) {
       this.userId = userId;
       this.translationId = translationId;
       this.dateAdded = dateAdded;
       this.learned = learned;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getTranslationId() {
        return this.translationId;
    }
    
    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }
    public Date getDateAdded() {
        return this.dateAdded;
    }
    
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    public boolean isLearned() {
        return this.learned;
    }
    
    public void setLearned(boolean learned) {
        this.learned = learned;
    }




}


