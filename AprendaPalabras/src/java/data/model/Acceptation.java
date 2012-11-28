package data.model;
// Generated 12-jun-2012 14:23:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Acceptation generated by hbm2java
 */
public class Acceptation  implements java.io.Serializable {


     private Integer id;
     private String acceptation;
     private Date creationDate;
     private int userId;

    public Acceptation() {
    }

    public Acceptation(String acceptation, Date creationDate, int userId) {
       this.acceptation = acceptation;
       this.creationDate = creationDate;
       this.userId = userId;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAcceptation() {
        return this.acceptation;
    }
    
    public void setAcceptation(String acceptation) {
        this.acceptation = acceptation;
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

