/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author https://community.jboss.org/wiki/GenericDataAccessObjects
 */
public interface IGenericDAO<T, ID extends Serializable> {
    T findById(ID id, boolean lock);
    List<T> findAll();
 
    /*
     * Devuelve una lista con los objetos que coincidan con el indicado por parámetro.
     */
    List<T> findByExample(T exampleInstance);
 
    /*
     * LLama al método saveOrUpdate
     */
    T makePersistent(T entity);
 
    /*
     * Llama al método delete
     */
    void makeTransient(T entity);
}
