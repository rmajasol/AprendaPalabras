/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.VO;

/**
 *
 * @author yomac
 */
public class AcceptationDataVO {

    private boolean createdByUser;
    private Integer uctaId;
    private boolean reported;
    private int reportedCount;

    public AcceptationDataVO() {
    }

    
    public AcceptationDataVO(Integer uctaId) {
        this.uctaId = uctaId;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
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

    public Integer getUctaId() {
        return uctaId;
    }

    public void setUctaId(Integer uctaId) {
        this.uctaId = uctaId;
    }
}
