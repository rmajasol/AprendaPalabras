/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import exceptions.WordNotFoundException;
import data.GenericHibernateDAO;
import data.model.Language;
import data.model.Word;
import data.model.WordLanguage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import error.ErrorMsgs;
import java.util.Date;

/**
 *
 * @author yomac
 */
public class WordLanguageDAO extends GenericHibernateDAO<WordLanguage, Integer> {

    /**
     * Devuelve un objeto WordLanguage que contenga la palabra y el lenguaje dado
     * @param word
     * @param lang
     * @return la id de la palabra para el lenguaje indicado
     * @throws WordNotFoundException 
     */
    public WordLanguage findWordLanguage(String word, String lang) throws WordNotFoundException {
        Word w = new WordDAO().findUniqueByCriteria(
                Restrictions.eq("word", word));
        Language l = new LanguageDAO().findUniqueByCriteria(
                Restrictions.eq("language", lang));
        Criterion c1 = Restrictions.eq("word", w.getId());
        Criterion c2 = Restrictions.eq("languageId", l.getId());
        WordLanguage wl = new WordLanguageDAO().findUniqueByCriteria(c1, c2);
        if (wl == null) {
            throw new WordNotFoundException(ErrorMsgs.WORD_NOT_FOUND
                    + " para el idioma <b>" + lang + "</b>");
        }
        return wl;
    }

    public boolean exists(WordLanguage wl) {
        return !findByExample(wl).isEmpty();
    }

    /**
     * devuelve una instancia wl a partir de una id de palabra y lenguaje dadas
     * @param wordId
     * @param languageId
     * @return 
     */
    public WordLanguage findUnique(int wordId, int languageId) {
        return findUniqueByCriteria(
                Restrictions.eq("wordId", wordId),
                Restrictions.eq("languageId", languageId));
    }

    /**
     * crea el wl si no está y busca su id
     * @param wordId
     * @param langId
     * @param userId
     * @return 
     */
    public int createIfNotExists(int wordId, int langId, int userId) {
        WordLanguage wl = findUnique(wordId, langId);
        if (wl == null) {
            WordLanguage wlNew = new WordLanguage(wordId, langId, new Date(), userId);
            return makePersistent(wlNew).getId();
        }
        return wl.getId();
    }

    public int createWordLanguage(String lr, String lf, String wr, String wf, int userId) {
        int wordId = new WordDAO().createWord(wr, wf, userId);
        int langId = new LanguageDAO().createLanguage(lr, lf, userId);
        if (exists(wordId, langId)) {
            return getId(wordId, langId);
        }
        WordLanguage wl = new WordLanguage(wordId, langId, new Date(), userId);
        return makePersistent(wl).getId();
    }

    private boolean exists(int wordId, int langId) {
        Criterion c1 = Restrictions.eq("wordId", wordId);
        Criterion c2 = Restrictions.eq("languageId", langId);
        return findUniqueByCriteria(c1, c2) != null;
    }

    private int getId(int wordId, int langId) {
        Criterion c1 = Restrictions.eq("wordId", wordId);
        Criterion c2 = Restrictions.eq("languageId", langId);
        return findUniqueByCriteria(c1, c2).getId();
    }

    public int getId(String word, String lang) {
        Word w = new WordDAO().findUniqueByCriteria(
                Restrictions.eq("word", word));
        Language l = new LanguageDAO().findUniqueByCriteria(
                Restrictions.eq("language", lang));
        Criterion c1 = Restrictions.eq("wordId", w.getId());
        Criterion c2 = Restrictions.eq("languageId", l.getId());
        return new WordLanguageDAO().findUniqueByCriteria(c1, c2).getId();
    }

    public void deleteIfOrphan(String word, String language) {
        int wordId = new WordDAO().getId(word);
        int languageId = new LanguageDAO().getId(language);
        int wlId = getId(wordId, languageId);
        Criterion c = Restrictions.eq("wordLanguageId", wlId);
        boolean hasParentWla = !new WlaDAO().findByCriteria(c).isEmpty();
        if (!hasParentWla) {
            WordLanguage wl = findById(wlId);
            makeTransient(wl);
        }
    }
    /**
     * comprueba si la palabra y el lenguaje dado existen
     */
//    public int getWLId(String word, String lang) {
//        
//        Query q = getSession().createQuery("FROM Word WHERE id = 50");
//        List li = q.list();
//        
//        
//        /**
//         * 
//         * ESTA MIERDA FUNCIONA!!!!! :)
//         */
//        String sql2 = "SELECT * FROM WordLanguage as wl WHERE wl.word_id=50";
//        Query query2 = getSession().createSQLQuery(sql2).
//                addEntity(WordLanguage.class);
////                setParameter("word", "sapato");
//        List l = query2.list();
//        WordLanguage wl1 = (WordLanguage) query2.uniqueResult();
//
//        
//        /**
//         * Y ESTA!!!!!!!!!!!!!!!!!!!!! 8===D'~ ~
//         */
//        String sql = "SELECT * FROM WordLanguage "
//                + "JOIN Word ON WordLanguage.word_id = Word.id "
//                + "JOIN Language ON WordLanguage.Language_id = Language.id "
//                + "WHERE Word.word = :word AND Language.language = :language";
//        Query query = getSession().createSQLQuery(sql).
//                addEntity(WordLanguage.class).
//                setParameter("word", "maravilloso").
//                setParameter("language", "Español");
//        WordLanguage wl = (WordLanguage) query.uniqueResult();
//        
////        return wl.getWordLanguageId();
//
//
//
////        for (Iterator iterator = workers.iterator(); iterator.hasNext();) {
////Worker worker = (Worker) iterator.next();
////System.out.println("Worker Id:" + worker.getWorkerId());
////System.out.println("Worker Name:" + worker.getFirstname());
////System.out.println("Worker Birth Date:" + worker.getBirthDate());
////System.out.println("Worker CellPhone No."+ worker.getCellphone());
////System.out.println("-------------------------------------------------------");
////}
//        
//        return 1;
//    }
//
//    
//    public int getId(String word, String lang) {
//        String sql = "SELECT * FROM WordLanguage as wl "
//                + "JOIN Word as w ON wl.word_id = w.id "
//                + "JOIN Language as l ON wl.Language_id = l.id "
//                + "WHERE w.word = :word AND l.language = :language";
//        Query query = getSession().createSQLQuery(sql).
//                addEntity(WordLanguage.class).
//                setParameter("word", word).
//                setParameter("language", lang);
//        WordLanguage wl = (WordLanguage) query.uniqueResult();
//        return wl.getId();
//    }
//    
//    public int getId(int wordId, int langId) {
//        String sql = "SELECT * FROM WordLanguage "
//                + "WHERE word_id = :wordId AND language_id = :langId";
//        Query query = getSession().createSQLQuery(sql).
//                addEntity(WordLanguage.class).
//                setParameter("wordId", wordId).
//                setParameter("langId", langId);
//        WordLanguage wl = (WordLanguage) query.uniqueResult();
//        return wl.getId();
//    }
//    
//    public int getId(String word, String lFrom, String lTo){
//        String sql =
//                "SELECT wl1.id, wl2.id, d1.definition, d2.definition, "
//                + "d1.creatitonDate, d2.creationDate, "
//                + "u.username, tr.creationDate, tr.id "
//                + ""
//                + "FROM Translation as tr "
//                + "JOIN WordLanguage as wl1 ON wl1.id = tr.WordLanguage1id "
//                + "JOIN WordLanguage as wl2 ON wl2.id = tr.WordLanguage2id "
//                + "JOIN Word as w1 ON wl1.Word_id = w1.id "
//                + "JOIN Word as w2 ON wl2.Word_id = w2.id "
//                + "JOIN Language as l1 ON wl1.Language_id = l1.id "
//                + "JOIN Language as l2 ON wl2.Language_id = l2.id "
//                + "JOIN User as u ON tr.User_id = u.id "
//                + ""
//                + "WHERE "
//                + "(w1.word = :word AND l1.language = :languageFrom AND "
//                + "l2.language = :languageTo) "
//                + "OR "
//                + "(w2.word = :word AND l2.language = :languageFrom AND "
//                + "l1.language = :languageTo)";
//
//        Query query = getSession().createSQLQuery(sql).
//                addScalar("w1.word", Hibernate.STRING).
//                addScalar("w2.word", Hibernate.STRING).
//                addScalar("u.username", Hibernate.STRING).
//                addScalar("tr.creationDate", Hibernate.TIMESTAMP).
//                addScalar("tr.id", Hibernate.INTEGER).
//                setParameter("word", word).
//                setParameter("languageFrom", lFrom).
//                setParameter("languageTo", lTo);
//        List l = query.list();
//
//        List<TranslationVO> trList = new ArrayList<TranslationVO>();
//
//        Iterator it = l.iterator();
//        while (it.hasNext()) {
//            Object[] r = (Object[]) it.next();
//            String w1 = (String) r[0];
//            String w2 = (String) r[1];
//            String w = w1.equals(word) ? w2 : w1;
//            String def1 = (String) r[2];
//            String def2 = (String) r[3];
//            String def1_crDate = (String) r[4];
//            String def2_crDate = (String) r[5];
//            String creator = (String) r[6];
//            Date d = (Date) r[7];
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm");
//            String tr_crDate = sdf.format(d);
//            int trId = (Integer) r[8];
//            TranslationVO tr = new TranslationVO();
//            tr.setDefinition(creator);
//            /*
//            //obtenemos la lista de contextos para la traducción
//            List<TrContextVO> cList = getTrContexts(trId);
//            //creamos el VO con todo
//            //lo añadimos a la lista de traducciones
//             * 
//             */
//            trList.add(tr);
//        }
//        return trList;
//    }
}
