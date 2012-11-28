/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * Elemento de la lista de traducciones creadas por el usuario
 * @author yomac
 */
public class CreatedTrVO1 {

    private int trId;
    private String wordFrom;
    private String langFrom;
    private String accepFrom;
    private String wordTo;
    private String langTo;
    private String accepTo;
    private Date creationDate;
//    private List<TrContextVO> contexts;

    public CreatedTrVO1(int trId, String wordFrom, String langFrom, String accepFrom, String wordTo, String langTo, String accepTo, Date creationDate) {
        this.trId = trId;
        this.wordFrom = wordFrom;
        this.langFrom = langFrom;
        this.accepFrom = accepFrom;
        this.wordTo = wordTo;
        this.langTo = langTo;
        this.accepTo = accepTo;
        this.creationDate = creationDate;
    }

    public String getAccepFrom() {
        return accepFrom;
    }

    public void setAccepFrom(String accepFrom) {
        this.accepFrom = accepFrom;
    }

    public String getAccepTo() {
        return accepTo;
    }

    public void setAccepTo(String accepTo) {
        this.accepTo = accepTo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(String langFrom) {
        this.langFrom = langFrom;
    }

    public String getLangTo() {
        return langTo;
    }

    public void setLangTo(String langTo) {
        this.langTo = langTo;
    }

    public int getTrId() {
        return trId;
    }

    public void setTrId(int trId) {
        this.trId = trId;
    }

    public String getWordFrom() {
        return wordFrom;
    }

    public void setWordFrom(String wordFrom) {
        this.wordFrom = wordFrom;
    }

    public String getWordTo() {
        return wordTo;
    }

    public void setWordTo(String wordTo) {
        this.wordTo = wordTo;
    }

    /**
     * invierte los valores si no coincide la palabra destino con la indicada por parámetro
     * @param hsr
     * @param ctr 
     */
    public void correct(String wordTo){
        if (!wordTo.equalsIgnoreCase(this.wordTo)) {
            String aux = wordFrom;
            wordFrom = wordTo;
            wordTo = aux;
            aux = langFrom;
            langFrom = langTo;
            langTo = aux;
            aux = accepFrom;
            accepFrom = accepTo;
            accepTo = aux;
        }
    }
    
}