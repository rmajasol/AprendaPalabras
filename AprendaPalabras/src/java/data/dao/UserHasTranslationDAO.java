/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.VO.TrContextVO;
import data.VO.TranslationVO;
import data.model.User;
import data.model.UserHasTranslation;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class UserHasTranslationDAO extends GenericHibernateDAO<UserHasTranslation, Integer> {

    public void addTrToUserList(int userId, int trId) {
        if (!isAdded(trId, userId)) {//así evitamos que se repitan en caso de recargar la página..
            UserHasTranslation uht = new UserHasTranslation(userId, trId, new Date(), false);
            makePersistent(uht);
        }
    }

    public List<TranslationVO> listAddedTranslations(int userId) {
        String sql =
                "SELECT uht.id, tr.id, w1.word, l1.language, acc1.acceptation, "
                + "w2.word, l2.language, acc2.acceptation, "
                + "uht.dateAdded, uht.learned "
                + ""
                + "FROM UserHasTranslation as uht "
                + ""
                + "JOIN Translation as tr ON tr.id = uht.Translation_id "
                + "JOIN User as u ON u.id = uht.User_id "
                + ""
                + "JOIN WLA as wla1 ON wla1.id = tr.WLA1_id "
                + "LEFT JOIN Acceptation as acc1 ON acc1.id = wla1.Acceptation_id "
                + "JOIN WLA as wla2 ON wla2.id = tr.WLA2_id "
                + "LEFT JOIN Acceptation as acc2 ON acc2.id = wla2.Acceptation_id "
                + ""
                + "JOIN WordLanguage as wl1 ON wl1.id = wla1.WordLanguage_id "
                + "JOIN WordLanguage as wl2 ON wl2.id = wla2.WordLanguage_id "
                + "JOIN Word as w1 ON wl1.Word_id = w1.id "
                + "JOIN Word as w2 ON wl2.Word_id = w2.id "
                + "JOIN Language as l1 ON wl1.Language_id = l1.id "
                + "JOIN Language as l2 ON wl2.Language_id = l2.id "
                + ""
                + ""
                + "WHERE uht.User_id = :userId";

        Query query = getSession().createSQLQuery(sql).
                addScalar("uht.id", Hibernate.INTEGER).
                addScalar("tr.id", Hibernate.INTEGER).
                addScalar("w1.word", Hibernate.STRING).
                addScalar("w2.word", Hibernate.STRING).
                addScalar("l1.language", Hibernate.STRING).
                addScalar("l2.language", Hibernate.STRING).
                addScalar("acc1.acceptation", Hibernate.STRING).
                addScalar("acc2.acceptation", Hibernate.STRING).
                addScalar("uht.dateAdded", Hibernate.TIMESTAMP).
                addScalar("uht.learned", Hibernate.BOOLEAN).
                setParameter("userId", userId);

        List l = query.list();
        session.close();

        List<TranslationVO> trList = new ArrayList<TranslationVO>();

        Iterator it = l.iterator();
        while (it.hasNext()) {
            Object[] r = (Object[]) it.next();
            int uhtId = (Integer) r[0];
            int trId = (Integer) r[1];
            String w1 = (String) r[2];
            String w2 = (String) r[3];
            String l1 = (String) r[4];
            String l2 = (String) r[5];
            String acc1 = (String) r[6];
            String acc2 = (String) r[7];
            Date dateAdded = (Date) r[8];
            boolean learned = (Boolean) r[9];
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//            String crDate = sdf.format(cd);
//            String dateAdded = sdf.format(da);
            TranslationVO tr = new TranslationVO(uhtId, trId, w1, w2, l1, l2, acc1,
                    acc2, dateAdded, learned);

            //sacamos su lista de contextos y la agregamos al VO
            List<TrContextVO> cList = new TrContextDAO().getTrContextList(tr.getTrId());
            tr.setContextList(cList);

            trList.add(tr);
        }
        return trList;
    }

    /**
     * actualiza la lista de traducciones encontradas indicando aquellas que
     * han sido añadidas a la lista personal del usuario y cuántos la añadieron en total
     * @param trList
     * @param userId 
     */
    public void setAdded(List<TranslationVO> trList, int userId) {
        for (TranslationVO tr : trList) {
            boolean isAddedByUser = isAdded(tr.getTrId(), userId) ? true : false;
            tr.setAdded(isAddedByUser);
            int numOfAdditions = addedCount(tr.getTrId());
            tr.setAddedCount(numOfAdditions);
        }
    }

    private boolean isAdded(int trId, int userId) {
        return findUniqueByCriteria(
                Restrictions.eq("translationId", trId),
                Restrictions.eq("userId", userId)) != null;
    }

    public void removeTrFromUserList(int userId, int trId) {
        UserHasTranslation uht = findUniqueByCriteria(
                Restrictions.eq("userId", userId),
                Restrictions.eq("translationId", trId));
        if (uht != null) {
            makeTransient(uht);
        }
    }

    public void markAsLearned(int userId, String myTrId) {
        UserHasTranslation uht = findUniqueByCriteria(
                Restrictions.eq("userId", userId),
                Restrictions.eq("id", Integer.parseInt(myTrId)));
        uht.setLearned(true);
        makePersistent(uht);
    }

    public void markAsNOTLearned(int userId, String myTrId) {
        UserHasTranslation uht = findUniqueByCriteria(
                Restrictions.eq("userId", userId),
                Restrictions.eq("id", Integer.parseInt(myTrId)));
        uht.setLearned(false);
        makePersistent(uht);
    }

    /**
     * Elimina todas las traducciones agregadas por el usuario
     */
    public void clearAddedTranslations(User user) {
        List<UserHasTranslation> addedTrs = findByCriteria(
                Restrictions.eq("userId", user.getId()));
        for (UserHasTranslation uht : addedTrs) {
            makeTransient(uht);
        }
    }

    public boolean addedOnlyByCreator(int trId, int userId) {
        return true;
    }

    public boolean isAddedByOthers(int trId, int userId) {
        Criterion c1 = Restrictions.eq("translationId", trId);
        Criterion c2 = Restrictions.ne("userId", userId);
        int c = countByCriteria(c1, c2);
        return c != 0;
    }
    
    public int addedCount(int trId){
        Criterion c = Restrictions.eq("translationId", trId);
        return countByCriteria(c);
    }

    
}
//    /**
//     * NUEVOOOO
//     * @param userId
//     * @return 
//     */
//    public List<UserHasTrVO> listAddedTranslations(int userId) {
//        String sql =
//                "SELECT {uht.*}, {w1.*}, {w2.*}, {l1.*}, {l2.*}, "
//                + "{acc1.*}, {acc2.*}, {u.*}, {tr.*} "
//                + ""
//                + "FROM UserHasTranslation as uht "
//                + ""
//                + "JOIN Translation as tr ON tr.id = uht.Translation_id "
//                + "JOIN User as u ON u.id = uht.User_id "
//                + ""
//                + "JOIN WLA as wla1 ON wla1.id = tr.WLA1_id "
//                + "LEFT JOIN Acceptation as acc1 ON acc1.id = wla1.Acceptation_id "
//                + "JOIN WLA as wla2 ON wla2.id = tr.WLA2_id "
//                + "LEFT JOIN Acceptation as acc2 ON acc2.id = wla2.Acceptation_id "
//                + ""
//                + "JOIN WordLanguage as wl1 ON wl1.id = wla1.WordLanguage_id "
//                + "JOIN WordLanguage as wl2 ON wl2.id = wla2.WordLanguage_id "
//                + "JOIN Word as w1 ON wl1.Word_id = w1.id "
//                + "JOIN Word as w2 ON wl2.Word_id = w2.id "
//                + "JOIN Language as l1 ON wl1.Language_id = l1.id "
//                + "JOIN Language as l2 ON wl2.Language_id = l2.id "
//                + ""
//                + ""
//                + "WHERE uht.User_id = :userId";
//
//        Query query = getSession().createSQLQuery(sql).
//                addEntity("uht", UserHasTranslation.class).
//                addEntity("w1", Word.class).
//                addEntity("w2", Word.class).
//                addEntity("l1", Language.class).
//                addEntity("l2", Language.class).
//                addEntity("acc1", Acceptation.class).
//                addEntity("acc2", Acceptation.class).
//                addEntity("u", User.class).
//                addEntity("tr", Translation.class).
//                setParameter("userId", userId);
//        
//        List l = query.list();
//
//        List<UserHasTrVO> trList = new ArrayList<UserHasTrVO>();
//
//        Iterator it = l.iterator();
//        while (it.hasNext()) {
//            Object[] r = (Object[]) it.next();
//            UserHasTranslation uht = (UserHasTranslation) r[0];
//            Word w1 = (Word) r[1];
//            Word w2 = (Word) r[2];
//            Language l1 = (Language) r[3];
//            Language l2 = (Language) r[4];
//            Acceptation acc1 = (Acceptation) r[5];
//            Acceptation acc2 = (Acceptation) r[6];
//            User u = (User) r[7];
//            Translation tr = (Translation) r[8];
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
////            String crDate = sdf.format(cd);
////            String dateAdded = sdf.format(da);
//
//            /**cargamos las salidas*/
//            UserHasTrVO uhtVo = new UserHasTrVO(uht);
//            WordVO wFrom = new WordVO(w1);
//            WordVO wTo = new WordVO(w2);
//            LanguageVO lFrom = new LanguageVO(l1);
//            LanguageVO lTo = new LanguageVO(l2);
//            AcceptationVO accFrom = new AcceptationVO(acc1);
//            AcceptationVO accTo = new AcceptationVO(acc2);
//            UserVO creator = new UserVO(u);
//            TranslationVO tvo = new TranslationVO(tr);
//
//            /**armamos el VO*/
//            WordLanguageVO wlFromVO = new WordLanguageVO(wFrom, lFrom);
//            WordLanguageVO wlToVO = new WordLanguageVO(wTo, lTo);
//            WlaVO wlaFrom = new WlaVO(wlFromVO, accFrom);
//            WlaVO wlaTo = new WlaVO(wlToVO, accTo);
//            tvo.setWlaFrom(wlaFrom);
//            tvo.setWlaTo(wlaTo);
//            tvo.setCreator(creator);
//            uhtVo.setTranslation(tvo);
//            trList.add(uhtVo);
//        }
//        return trList;
//    }