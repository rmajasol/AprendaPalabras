package data.dao;

import data.GenericHibernateDAO;
import data.model.Role;

/**
 * @see GenericHibernateDAO
 * @author rmajasol
 */
public class RoleDAO extends GenericHibernateDAO<Role, Integer> {

    /**
     * Creamos los roles si todavía no aparecen creados en la Base de datos.
     */
    public void createRolesIfNotExists() {
        if (findAll().isEmpty()) {
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            makePersistent(userRole);
            makePersistent(adminRole);
        }
    }
}
