/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.VO.AcceptationDataVO;
import data.VO.TranslationVO;
import data.VO.TrContextVO;
import data.model.Report;
import data.model.UserCreatesTrAccep;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class ReportDAO extends GenericHibernateDAO<Report, Integer> {

    /**
     * setea el VO de la lista de traducciones con todos los reportes
     * @param trList
     * @param userId 
     */
    public void setReported(List<TranslationVO> trList, int userId) {
        for (TranslationVO tr : trList) {
            //para la traducción
            boolean isReportedByUser = isReportedByUser_tr(tr.getTrId(), userId) ? true : false;
            tr.setReported(isReportedByUser);
            int reportedCount = getReportedCound_tr(tr.getTrId());
            tr.setReportedCount(reportedCount);
            int trId = tr.getTrId();


            //para la acepción mostrada
            boolean hasAccep = tr.getAccepFrom() != null ? true : false;
            //antes de todo vemos si hay acepción
            if (hasAccep) {
                Integer accepId = new AcceptationDAO().getId(tr.getAccepFrom());
                UserCreatesTrAccep ucta = new UserCreatesTrAccepDAO().getUcta(
                        tr.getTrId(), accepId, userId);
                if (ucta != null) {
                    AcceptationDataVO accepData = tr.getAccepData();
                    isReportedByUser = isReportedByUser_accep(ucta.getId(), userId);
                    accepData.setReported(isReportedByUser);
                    tr.setAccepData(accepData);
                } else {
                    AcceptationDataVO accepData = tr.getAccepData();
                    accepData.setReported(false);
                    tr.setAccepData(accepData);
                }
            }

            //para los contextos
            for (TrContextVO trc : tr.getContextList()) {
                isReportedByUser = isReportedByUser_context(trId, trc.getId(), userId)
                        ? true : false;
                trc.setReported(isReportedByUser);
                reportedCount = getReportedCount_context(trc.getId());
                trc.setReportedCount(reportedCount);
            }
        }
    }

    /**
     * si la traducción fue reportada por el usuario
     * @param trId
     * @param userId
     * @return 
     */
    public boolean isReportedByUser_tr(int trId, int userId) {
        return findUniqueByCriteria(
                Restrictions.eq("translationId", trId),
                Restrictions.eq("userId", userId)) != null;
    }

    public boolean isReported(int trId) {
        return !findByCriteria(
                Restrictions.eq("translationId", trId)).isEmpty();
    }

    public boolean isReportedByUser_accep(Integer uctaId, int userId) {
        return findUniqueByCriteria(
                Restrictions.eq("userCreatesTrAccepId", uctaId),
                Restrictions.eq("userId", userId)) != null;
    }

    public boolean isReportedByUser_context(int trId, int cId, int userId) {
        return findUniqueByCriteria(
                Restrictions.eq("translationContextId", cId),
                Restrictions.eq("userId", userId)) != null;
    }

    /**
     * numero de usuarios que reportaron la traducción
     * @param trId
     * @return 
     */
    private int getReportedCound_tr(int trId) {
        Criterion c = Restrictions.eq("translationId", trId);
        return countByCriteria(c);
    }

    private int getReportedCount_accep(int uctaId) {
        Criterion c = Restrictions.eq("userCreatesTrAccep", uctaId);
        return countByCriteria(c);
    }

    private int getReportedCount_context(int cId) {
        Criterion c = Restrictions.eq("translationContextId", cId);
        return countByCriteria(c);
    }

    /**
     * 
     * @param cId la PK del contexto
     * @param userId la PK del usuario que emite el reporte
     */
    public void createContextReport(int cId, int userId) {
        Criterion c1 = Restrictions.eq("translationContextId", cId);
        Criterion c2 = Restrictions.eq("userId", userId);
        if (!exists(c1, c2)) {
            Report r = new Report(userId);
            r.setTranslationContextId(cId);
            makePersistent(r);
        }
    }

    /**
     * Elimina un reporte para el contexto dato el id del contexto y el id
     * del usuario que realiza el reporte
     * @param cId
     * @param userId 
     */
    public void deleteContextReport(int cId, int userId) {
        Criterion c1 = Restrictions.eq("translationContextId", cId);
        Criterion c2 = Restrictions.eq("userId", userId);
        if (exists(c1, c2)) {
            Report r = findUniqueByCriteria(c1, c2);
            makeTransient(r);
        }
    }

    /**
     * Elimina todos los reportes con el ucta dado
     * @param uctaId 
     */
    public void deleteReports_forUcta(Integer uctaId) {
        if (uctaId == null) {
            return;
        }
        Criterion c = Restrictions.eq("userCreatesTrAccepId", uctaId);
        List<Report> list = findByCriteria(c);
        for (Report r : list) {
            makeTransient(r);
        }
    }

    /**
     * Elimina todos los reportes para un contexto dado por su id
     * @param id 
     */
    public void deleteReports_forContext(Integer cId) {
        Criterion c = Restrictions.eq("translationContextId", cId);
        List<Report> list = findByCriteria(c);
        for (Report r : list) {
            makeTransient(r);
        }
    }

    /**
     * Elimina todos los reportes para una traducción dada por su id
     * @param trId 
     */
    public void deleteReports_forTranslation(int trId) {
        Criterion c = Restrictions.eq("translationId", trId);
        List<Report> list = findByCriteria(c);
        for (Report r : list) {
            makeTransient(r);
        }
    }
}
