/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author yomac
 */
public interface IDAOPersistence {

    public boolean create(Object obj);

    public Object read(String code);

    public boolean update(Object obj, String condition);

    public boolean delete(String condition);

    public ArrayList<Object> list(String condition);
}
