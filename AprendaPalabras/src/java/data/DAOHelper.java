/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author http://www.java2s.com/Code/Java/Hibernate/GenericDaoCreate.htm
 */
public class DAOHelper  {

    private Session session;
    private Transaction tx;

    protected void saveOrUpdate(Object obj) {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }
    }

    protected void delete(Object obj) {
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }
    }

    protected Object find(Class clazz, Long id) {
        Object obj = null;
        try {
            startOperation();
            obj = session.load(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }
        return obj;
    }

    protected List findAll(Class clazz) {
        List objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }
        return objects;
    }

    protected void startOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    protected void handleException(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
    }

    /*
     * CREATE FUNCTIONS
     */
    public void create(Object obj) {
        try {
            startOperation();
            session.save(obj);
            tx.commit();
        } catch (HibernateException he) {
            handleException(he);
            throw he;
        } finally {
            session.close();
        }
    }

    /*
     * READ FUNCTIONS
     */
    public Object readUnique(String table, String column, String value) throws ClassNotFoundException {
        startOperation();
        Criteria criteria = session.createCriteria(Class.forName(table).getName());
        criteria.add(Restrictions.eq(column, value));
        return criteria.uniqueResult();
    }

    public Object readByID(int id, String table) throws ClassNotFoundException {
        Object obj = null;
        try {
            startOperation();
            obj = session.get(Class.forName(table), id);
        } finally {
            session.close();
        }
        return obj;
    }

    //lee la lista de objetos encontrados a partir del valor del atributo de la tabla
    public List<Object> readList(String table, String column, String value) {
        List objectList = null;
        try {
            startOperation();
            //Create Select Clause HQL
            Query query = session.createQuery("from " + table + " where " + column + " = :" + column);
            query.setParameter(column, value);
            objectList = query.list();
//            Iterator it = query.iterate();
//            if (it.hasNext()) {
//                id = (User) it.next().;
//            }
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.close();
        }
        return objectList;
    }

    /**
     * @param table Entidad a consultar.
     * @param column Atributo de la entidad.
     * @param value Valor del atributo.
     * @return {@code true} si existe en la BD.
     * @throws ClassNotFoundException si no se encuentra la clase que representa el 
     * POJO de la entidad a consultar.
     */
    public boolean exists(String table, String column, String value) throws ClassNotFoundException {
        startOperation();
        Criteria criteria = session.createCriteria(Class.forName(table).getName()).add(Restrictions.eq(column, value));
        return criteria.uniqueResult() == null ? false : true;
    }

    //devuelve la lista de todos los objetos de una tabla
    public List list(String table) {
        List list = null;
        try {
            startOperation();
            list = session.createQuery("from " + table).list();
        } finally {
            session.close();
        }
        return list;
    }
    /*
     * UPDATE FUNCTIONS
     */
    /*
     * DELETE FUNCTIONS
     */
}