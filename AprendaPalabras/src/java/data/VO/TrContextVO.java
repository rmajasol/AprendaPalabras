/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO;

import java.util.Date;

/**
 * Elemento de la lista de traducciones creadas por el usuario
 * @author yomac
 */
public class TrContextVO {

    private int id;
    private String phraseFrom;
    private String phraseFrom_lang;
    private PhraseVO phraseFromVo;
    private String phraseTo;
    private String phraseTo_lang;
    private PhraseVO phraseToVo;
    private Date creationDate;
    private String userCreatorName;
    private int userCreatorId;
    private boolean reported; //si el usuario ya ha reportado el contexto
    private int reportedCount;
    private boolean createdByUser;
    /**
     * indica si se intercambiaron las frases de contexto
     */
    private boolean swapped = false;

    public TrContextVO(int id, PhraseVO phraseFrom, PhraseVO phraseTo, Date creationDate,
            String userCreatorName, int userCreatorId) {
        this.id = id;
        this.phraseFromVo = phraseFrom;
        this.phraseToVo = phraseTo;
        this.creationDate = creationDate;
        this.userCreatorName = userCreatorName;
        this.userCreatorId = userCreatorId;
    }

    public TrContextVO(int id, String phraseFrom, String phraseFrom_lang, String phraseTo, String phraseTo_lang, Date creationDate, String userCreatorName, int userCreatorId) {
        this.id = id;
        this.phraseFrom = phraseFrom;
        this.phraseFrom_lang = phraseFrom_lang;
        this.phraseTo = phraseTo;
        this.phraseTo_lang = phraseTo_lang;
        this.creationDate = creationDate;
        this.userCreatorName = userCreatorName;
        this.userCreatorId = userCreatorId;
    }

    public void swapVO() {
        PhraseVO aux = phraseFromVo;
        phraseFromVo.setPhrase(phraseToVo.getPhrase());
        phraseToVo.setPhrase(aux.getPhrase());
    }

    public void swap() {
        String aux = phraseFrom;
        phraseFrom = phraseTo;
        phraseTo = aux;
        swapped = true;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(int reportedCount) {
        this.reportedCount = reportedCount;
    }

    public String getPhraseFrom() {
        return phraseFrom;
    }

    public void setPhraseFrom(String phraseFrom) {
        this.phraseFrom = phraseFrom;
    }

    public PhraseVO getPhraseFromVo() {
        return phraseFromVo;
    }

    public void setPhraseFromVo(PhraseVO phraseFromVo) {
        this.phraseFromVo = phraseFromVo;
    }

    public String getPhraseTo() {
        return phraseTo;
    }

    public void setPhraseTo(String phraseTo) {
        this.phraseTo = phraseTo;
    }

    public PhraseVO getPhraseToVo() {
        return phraseToVo;
    }

    public void setPhraseToVo(PhraseVO phraseToVo) {
        this.phraseToVo = phraseToVo;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public int getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(int userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getUserCreatorName() {
        return userCreatorName;
    }

    public void setUserCreatorName(String userCreatorName) {
        this.userCreatorName = userCreatorName;
    }

    public String getPhraseFrom_lang() {
        return phraseFrom_lang;
    }

    public void setPhraseFrom_lang(String phraseFrom_lang) {
        this.phraseFrom_lang = phraseFrom_lang;
    }

    public String getPhraseTo_lang() {
        return phraseTo_lang;
    }

    public void setPhraseTo_lang(String phraseTo_lang) {
        this.phraseTo_lang = phraseTo_lang;
    }

    public boolean isSwapped() {
        return swapped;
    }

    public void setSwapped(boolean swapped) {
        this.swapped = swapped;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
    }
}