/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO;

import java.util.Date;
import java.util.List;

/**
 * Elemento de la lista de traducciones creadas por el usuario
 * @author yomac
 */
public class TranslationVO {

    private int uhtId;
    private int trId;
    private String wordFrom;
    private String langFrom;
    private String accepFrom;
    private String wordTo;
    private String langTo;
    private String accepTo;
    private AcceptationDataVO accepData = new AcceptationDataVO();
    private List<TrContextVO> contextList;
    private Date creationDate;
    private String userCreatorName;
    private int userCreatorId;
    private boolean added;
    private int addedCount;
    private boolean createdByUser;
    /**
     * REPORTS
     */
    private boolean reported;
    private int reportedCount;
    /**
     * para la lista personal
     */
    private Date dateAdded;
    private boolean learned;
    /**
     * indica si se invirtió el origen por el destino
     */
    private boolean swapped = false;

    /**
     * para la lista de traducciones encontradas o escoger una traducción
     */
    public TranslationVO(int trId, String wordFrom, String langFrom, String accepFrom,
            String wordTo, String langTo, String accepTo, Date creationDate,
            String userCreatorName, int userCreatorId) {
        this.trId = trId;
        this.wordFrom = wordFrom;
        this.langFrom = langFrom;
        this.accepFrom = accepFrom;
        this.wordTo = wordTo;
        this.langTo = langTo;
        this.accepTo = accepTo;
        this.creationDate = creationDate;
        this.userCreatorName = userCreatorName;
        this.userCreatorId = userCreatorId;
    }

    /**
     * constructor para la lista personal
     * @return 
     */
    public TranslationVO(int uhtId, int trId, String wordFrom, String wordTo,
            String langFrom, String langTo, String accepFrom, String accepTo,
            Date dateAdded, boolean learned) {
        this.uhtId = uhtId;
        this.trId = trId;
        this.wordFrom = wordFrom;
        this.wordTo = wordTo;
        this.langFrom = langFrom;
        this.langTo = langTo;
        this.accepFrom = accepFrom;
        this.accepTo = accepTo;
        this.dateAdded = dateAdded;
        this.learned = learned;
    }

    /**
     * constructor para la lista de traducciones creadas
     * @return 
     */
    public TranslationVO(int trId, String wordFrom, String wordTo, String langFrom,
            String langTo, String accepFrom, String accepTo, Date creationDate) {
        this.trId = trId;
        this.wordFrom = wordFrom;
        this.wordTo = wordTo;
        this.langFrom = langFrom;
        this.langTo = langTo;
        this.accepFrom = accepFrom;
        this.accepTo = accepTo;
        this.creationDate = creationDate;
    }

    /**
     * invierte los valores si no coincide la palabra destino con la indicada por parámetro
     * @param hsr
     * @param ctr 
     */
    public void swap(String wordTo, String langTo) {
        if (!(wordTo.equalsIgnoreCase(this.wordTo)
                && langTo.equalsIgnoreCase(this.langTo))) {
            swap();
        }
    }

//    public void swap2(String wordFrom, String langFrom) {
//        if (!(wordFrom.equalsIgnoreCase(this.wordFrom)
//                && langFrom.equalsIgnoreCase(this.langFrom))) {
//        }
//    }
    public void swap() {
        String aux = wordFrom;
        wordFrom = wordTo;
        wordTo = aux;
        aux = langFrom;
        langFrom = langTo;
        langTo = aux;
        aux = accepFrom;
        accepFrom = accepTo;
        accepTo = aux;
        //y finalmente lo marcamos como invertido
        swapped = true;
    }

    public void swapContexts() {
        if (contextList != null) {
            for (TrContextVO trc : contextList) {
                if (!trc.getPhraseFrom_lang().equalsIgnoreCase(langFrom)) {
                    trc.swap();
                }
            }
        }
    }

    public void setContextList(List<TrContextVO> contextList) {
        this.contextList = contextList;
        swapContexts();
    }

    public void swapToDefLangs(String defLangFrom, String defLangTo) {
        if (defLangFrom != null && has(defLangFrom)) {
            if (!defLangFrom.equalsIgnoreCase(this.langFrom)) {
                swap();

            }
        }
        if (defLangTo != null && has(defLangTo)) {
            if (!defLangTo.equalsIgnoreCase(this.langTo)) {
                swap();
            }
        }
        swapContexts();
    }

    /**
     * mira si en la traducción está este lenguage en alguno de los dos
     * @param lang
     * @return 
     */
    public boolean has(String lang) {
        return lang.equalsIgnoreCase(this.langFrom) || lang.equalsIgnoreCase(this.langTo);
    }

    /**
     * 
     * 
     * 
     * GETTERS & SETTERS
     */
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

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public int getAddedCount() {
        return addedCount;
    }

    public void setAddedCount(int addedCount) {
        this.addedCount = addedCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
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

    public boolean isLearned() {
        return learned;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public int getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(int reportedCount) {
        this.reportedCount = reportedCount;
    }

    public boolean isSwapped() {
        return swapped;
    }

    public void setSwapped(boolean swapped) {
        this.swapped = swapped;
    }

    public int getTrId() {
        return trId;
    }

    public void setTrId(int trId) {
        this.trId = trId;
    }

    public int getUhtId() {
        return uhtId;
    }

    public void setUhtId(int uhtId) {
        this.uhtId = uhtId;
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

    public List<TrContextVO> getContextList() {
        return contextList;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
    }

    public AcceptationDataVO getAccepData() {
        return accepData;
    }

    public void setAccepData(AcceptationDataVO accepData) {
        this.accepData = accepData;
    }
}