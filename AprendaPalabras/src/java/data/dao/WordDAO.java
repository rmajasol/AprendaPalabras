/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.GenericHibernateDAO;
import data.model.Word;
import java.util.Date;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yomac
 */
public class WordDAO extends GenericHibernateDAO<Word, Integer> {

    public int getId(String word) {
        return findUniqueByCriteria(
                Restrictions.eq("word", word).ignoreCase()).getId();
    }

    public boolean exists(String word) {
        return new WordDAO().exists("word", word);
    }

    /**
     * devuelve la instancia a partir de la palabra
     * @param word
     * @return 
     */
    public Word findUnique(String word) {
        return findUniqueByCriteria(Restrictions.eq("word", word));
    }

    /**
     * lo guarda si no existe y saca su id
     * @param word
     * @param userId
     * @return 
     */
    public int createIfNotExists(String word, int userId) {
        Word w = new Word();
        w.setWord(word);
        Word w2 = findUnique(word);
        if (w2 == null) {
            w.setCreationDate(new Date());
            w.setUserId(userId);
            return makePersistent(w).getId();
        }
        return w2.getId();
    }

    public int createWord(String wr, String wf, int userId) {
        if (wr.equalsIgnoreCase(wf)) {
            return getId(wr);
        }
        return createWord(wf, userId);
    }

    /**
     * Elimina una palabra si no es usada por ninguna traducción
     * @param word
     * @param id 
     */
    public void deleteIfOrphan(String word) {
        int wordId = getId(word);
        boolean hasParentWordLanguage = new WordLanguageDAO().exists("wordId", wordId);
        if (!hasParentWordLanguage) {
            Word w = findById(wordId);
            makeTransient(w);
        }
    }

    public int createWord(String word, int userId) {
        if (!exists(word)) {
            Word w = new Word(word, new Date(), userId);
            return makePersistent(w).getId();
        }
        return getId(word);
    }
    /**
     * Devuelve la id de la WordLanguage donde wordId y languageId se corresponden
     * con la palabra y lenguaje origen introducido
     * @param word
     * @param lang
     * @return la id de la palabra para el lenguaje indicado
     * @throws WordNotFoundException 
     */
//    public int getId(String word, String lang) throws WordNotFoundException {
//       
//    }
//
//    /**
//     * comprueba si la palabra y el lenguaje dado existen
//     */
//    public boolean getId(String word, String lang) {
//       
//        
//        
//        for (Iterator iterator = workers.iterator(); iterator.hasNext();) {
//Worker worker = (Worker) iterator.next();
//System.out.println("Worker Id:" + worker.getWorkerId());
//System.out.println("Worker Name:" + worker.getFirstname());
//System.out.println("Worker Birth Date:" + worker.getBirthDate());
//System.out.println("Worker CellPhone No."+ worker.getCellphone());
//System.out.println("-------------------------------------------------------");
//}
//    }
}
