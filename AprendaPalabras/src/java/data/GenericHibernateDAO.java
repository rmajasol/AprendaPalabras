package data;

import data.dao.TranslationDAO;
import data.dao.UserDAO;
import data.model.Translation;
import data.model.User;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * <p>Esta es superclase de todas las clases DAO, donde en cada uno de ellas se
 * definen los tipos genéricos T e ID. Por ejemplo para {@link UserDAO} T es
 * {@link User} y para {@link TranslationDAO} T se corresponde con 
 * {@link Translation}.
 * ID siempre será definido como {@link Integer} para todas las clases heredadas.
 * 
 * <p>De esta forma, gracias al uso de la genericidad en Java evitamos repetir 
 * todos los métodos definidos en esta clase para cada uno de los DAO.
 * 
 * @author rmajasol
 * @see <p>Obtenido y modificado a partir del ejemplo mostrado en el libro
 * "<a href=http://jpwh.org/examples/jpwh/caveatemptor-native-061211.zip>Hibernate
 * in Action</a>"</p> 
 * 
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable> {

    /** clase genérica T */
    protected Class<T> persistentClass;
    /** sesión de Hibernate */
    protected Session session;

    /**
     * Constructor que inicializa la variable {@link #persistentClass} con la
     * clase genérica T
     */
    public GenericHibernateDAO() {
        this.persistentClass =
                (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Setter para el atributo {@link #session}
     * @param s objeto {@link Session} a guardar sobre el atributo seteado.
     */
    public void setSession(Session s) {
        this.session = s;
    }

    /**
     * @return objeto sesión de Hibernate
     * @throws HibernateException en caso de haber error al crear la sesión y 
     * guardarla en {@link #session}
     */
    protected Session getSession() throws HibernateException {
        if (session == null) {
            session = HibernateUtil.getSessionFactory().openSession();
        } else if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    /**
     * @return clase genérica T
     */
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * @param id objeto de tipo genérico ID. En todos los casos de clases
     * heredadas que usan genericidad este tipo es {@link Integer}
     * @return objeto de tipo genérico T correspondiente a una entidad mapeada
     * por Hibernate y cuyo atributo {@code id} coincide con el introducido por 
     * parámetro.
     */
    public T findById(ID id) {
        return (T) getSession().load(getPersistentClass(), id);
    }

    /**
     * @see #findByExample(java.lang.Object) 
     * @param exampleInstance
     * @return objeto genérico de tipo T encontrado
     */
    public T findUniqueByExample(T exampleInstance) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        crit.add(example);
        T t = (T) crit.uniqueResult();
        session.close();
        return t;
    }

    /**
     * @see #findByCriteria(org.hibernate.criterion.Criterion[]) 
     * @param criterion
     * @return objeto de tipo genérico T encontrado.
     */
    public T findUniqueByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        T t = (T) crit.uniqueResult();
        session.close();
        return t;

    }

    /**
     * @see #exists(java.lang.String, int) 
     */
    public boolean exists(String column, String value) {
        Criterion criterion = Restrictions.eq(column, value).ignoreCase();
        if (!findByCriteria(criterion).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @param column nombre para el atributo sobre el que se aplica el criterio
     * de selección.
     * @param value valor del atributo para aplicar el criterio de selección.
     * @return {@code true} si existe algún registro con el criterio de 
     * selección definido a partir de los argumentos dados.
     */
    public boolean exists(String column, int value) {
        Criterion criterion = Restrictions.eq(column, value);
        if (!findByCriteria(criterion).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @see #findByCriteria(org.hibernate.criterion.Criterion[]) 
     * @param criterion
     * @return {@code true} si existe algún registro con el criterio de 
     * selección definido.
     */
    public boolean exists(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        List<T> l = crit.list();
        session.close();
        return !l.isEmpty();
    }

    /**
     * 
     * @return lista de objetos del tipo genérico T
     */
    public List<T> findAll() {
        return findByCriteria();
    }

    /**
     * 
     * @param order objeto que define el tipo de orden para los resultados
     * de la operación de selección sobre la Base de datos.
     * @return lista de objetos del tipo genérico T
     */
    public List<T> findAllOrdered(Order order) {
        return findByCriteriaOrdered(order);
    }

    /**
     * @param exampleInstance objeto a encontrar en la lista
     * @return lista de objetos del tipo genérico T cuyos atributos tengan
     * el mismo valor que el introducido por parámetro.
     */
    public List<T> findByExample(T exampleInstance) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        crit.add(example);
        List<T> l = crit.list();
        session.close();
        return l;
    }

    /**
     * 
     * @param entity objeto de tipo genérico T que corresponde a una clase
     * mapeada por Hibernate.
     * @return objeto de tipo genérico T guardado en la Base de datos.
     */
    public T makePersistent(T entity) {
        try {
            getSession().saveOrUpdate(entity);
            getSession().beginTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }
        return entity;
    }

    /**
     * Elimina de la Base de datos el registro correspondiente a la entidad
     * mapeada por el objeto de tipo genérico T introducido por parámetro.
     * @param entity objeto de tipo genérico T que corresponde a una clase
     * mapeada por Hibernate.
     */
    public void makeTransient(T entity) {
        getSession().delete(entity);
        getSession().beginTransaction().commit();
        session.close();
    }

    /**
     * @see http://mundogeek.net/archivos/2009/04/04/varargs-en-java/
     * @param criterion número de argumentos variable, donde cada uno es un
     * criterio de selecicón {@code Criterion} sobre la Base de datos, a usar 
     * por la clase Criteria para la consulta.
     * @return lista de objetos de tipo genérico T
     */
    public List<T> findByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        List<T> l = crit.list();
        session.close();
        return l;
    }

    /**
     * @see #findByCriteria(org.hibernate.criterion.Criterion[])
     * @see #findAllOrdered(org.hibernate.criterion.Order) 
     * @param order
     * @param criterion
     * @return 
     */
    protected List<T> findByCriteriaOrdered(Order order, Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        crit.addOrder(order);
        List<T> l = crit.list();
        session.close();
        return l;
    }

    /**
     * Esta función es invocada en cada bloque para el tratamiento de la excepción
     * {@link HibernateException}. Se pueden anidar transacciones. Para cancelar
     * la actual y volver un nivel más arriba empleamos el médodo 
     * beginTransaction().rollback() sobre la sesión de Hibernate.
     * @param he la excepción lanzada por el método
     * @throws HibernateException vuelve a lanzar la misma excepción capturada
     */
    protected void handleException(HibernateException he) throws HibernateException {
        session.beginTransaction().rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
    }

    /**
     * @see #findByCriteria(org.hibernate.criterion.Criterion[]) 
     * @param criterion
     * @return número de registros que coinciden con los criterios de selección
     * definidos sobre la Base de datos.
     */
    public int countByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        int n = (Integer) crit.setProjection(Projections.rowCount()).uniqueResult();
        session.close();
        return n;
    }
}
