/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import exceptions.MailNotSentException;
import data.dao.AccessKeyDAO;
import mail.ValidationMail;
import data.dao.LanguageDAO;
import data.dao.UserDAO;
import error.ErrorMsgs;
import exceptions.WrongFormException;
import data.dao.RoleDAO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Date;
import data.model.AccessKey;
import data.model.Role;
import data.model.User;
import error.Errors;
import exceptions.PasswordEncriptionException;
import form.repeatedFields.FieldEmailRep;
import form.compounds.LanguageSelection;
import form.repeatedFields.FieldPasswordRep;
import form.singleFields.FieldRecaptcha;
import form.singleFields.FieldUsername;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.servlet.ModelAndView;
import utils.Encripter;
import utils.Common;
import utils.words.Urls;
import utils.words.Views;

/**
 * Clase de servicio usada por la clase controladora 
 * {@link RegistrationController}
 * 
 * @author rmajasol
 */
public class RegistrationService extends ServiceHelper {

    public RegistrationService(HttpServletRequest hsr, HttpServletResponse hsr1) {
        super(hsr, hsr1);
    }
    /** 
     * Representa la cadena de 50 caracteres alfanuméricos generados de manera
     * aleatoria. Inicializada en el método {@link #sendValidationMail()}
     */
    private String accessKey;

    /**
     * Se le envía al usuario un email con la clave de verificación 
     * {@link #accessKey} y el lenguaje elegido.
     * 
     * @throws MailNotSentException si hubo algún problema con el envío.
     */
    public void sendValidationMail() throws MailNotSentException {
        try {
            accessKey = RandomStringUtils.randomAlphanumeric(80);
            /** escogemos el lenguaje elegido en el formulario de registro */
            String chosenLang =
                    new LanguageSelection(hsr, "lang").getFieldValue();
            /** construimos el mail y lo enviamos */
            ValidationMail vm = new ValidationMail(accessKey, chosenLang, hsr);
            vm.send();
        } catch (Exception ex) {
            throw new MailNotSentException(ErrorMsgs.FAILURE_SENDING_EMAIL);
        }
    }

    /**
     * <ol>
     * <li>Se crea un registro para el usuario en la BD con la contraseña encriptada,
     * de forma que en caso de ataque al servidor no se pueda conocer qué contraseñas
     * escribieron los usuarios para registrarse y acceder a la aplicación.</li>
     * <li>Se crea una clave para verificar el usuario.</li>
     * </ol>
     * 
     * @throws PasswordEncriptionException si hubo algún problema con encriptar
     * la contraseña.
     */
    public void createUser() throws PasswordEncriptionException {
        try {
            /** declaramos el objeto que mapea la entidad User */
            User u = new User();
            u.setUsername(hsr.getParameter("username").trim());
            String passEnc = Encripter.encPassword(hsr.getParameter("password"));
            u.setPassword(passEnc);
            u.setEmail(hsr.getParameter("email"));
            u.setRegistrationDate(new Date());
            u.setIpAddressReg(hsr.getRemoteAddr());
            u.setValidated(false);

            u.setInvertAcceptations(false);
            u.setHideDefLangFrom(false);
            u.setHideDefLangTo(false);
            u.setAutoAdding(false);
            u.setHideUsername(false);

            new RoleDAO().createRolesIfNotExists();
            u.setRoleId(new RoleDAO().findUniqueByExample(new Role("user")).getId());

            /** creamos un registro para el usuario en la Base de datos */
            new UserDAO().makePersistent(u);

            /** creamos la clave para validar el usuario */
            AccessKey ak = new AccessKey();
            int userId = new UserDAO().findUniqueByExample(u).getId();
            ak.setUserId(userId);
            ak.setAccessKey(accessKey);
            new AccessKeyDAO().makePersistent(ak);
        } catch (Exception ex) {
            throw new PasswordEncriptionException(ErrorMsgs.PASSWORD_ENCRIPT_ERROR);
        }
    }

    /**
     * Se valida el usuario previamente registrado siguiendo los siguientes pasos:
     * <ol>
     * <li>Comprueba si el valor del parámetro {@code key} obtenido vía GET en 
     * la petición HTTP corresponde con el valor de la clave de acceso almacenada 
     * en la tabla {@code AccessKey}.</li>
     * <li>Asigna el lenguaje elegido en el formulario de registro al usuario.
     * Si el lenguaje no existe en la BD se crea un nuevo registro para él en la
     * tabla {@code Language}. Si existe símplemente se asigna su id 
     * correspondiente al usuario mediante el setter 
     * {@link User#setLanguageId(java.lang.Integer) }</li>
     * <li>Si todo ha ido bien se elimina el registro para la clave de acceso
     * usada e introducimos el objeto {@link User} empleado dentro del método
     * como nuevo atributo para la variable de sesión {@link #session} con .</li>
     * <li><ul><li>Si no hay error la dirección a donde redirigir corresponderá
     * con la página de búsqueda (search.htm), indicando que se llega a ella 
     * tras haber sido validado con la variable 
     * introducida por parámetro GET en la peticción HTTP (?validated=yes).
     * <li>Si hay error, dando por ejemplo {@link NullPointerException} en caso de 
     * no existir ningún usuario con la clave de acceso asignada entonces la
     * redirección se hará hacia la página índice (index.htm)</ul></li>
     * </ol>
     * @throws Exception si ocurre algún error durante la validación
     */
    public void validateUser() throws Exception {
        String redirectedUrl = null;
        try {
            AccessKey ak = new AccessKeyDAO().getAccessKey(hsr.getParameter("key"));
            user = new UserDAO().getUser(ak);
            String lang = Common.forceUTF8(URLDecoder.decode(hsr.getParameter("lang"), "UTF-8"));

            new LanguageDAO().createLanguageIfNotExists(lang, user.getId());

            user.setLanguageId(new LanguageDAO().findUniqueByCriteria(
                    Restrictions.eq("language", lang)).getId());
            user.setValidated(true);
            new UserDAO().makePersistent(user);

            new AccessKeyDAO().makeTransient(ak);

            session = hsr.getSession();
            session.setAttribute("sessionUser", user);

            redirectedUrl = Urls.SEARCH + "?validated=yes";
        } catch (Exception ex) {
            errors.setLogicError(ex.getMessage());
            redirectedUrl = Urls.INDEX;
        } finally {
            hsr1.sendRedirect(redirectedUrl);
        }
    }

    public void checkForm() throws WrongFormException {
        new FieldUsername(hsr).checkToSave(hsr, errors);
        new FieldPasswordRep(hsr).check(hsr, errors);
        new FieldEmailRep(hsr).checkToSave(hsr, errors);
        new LanguageSelection(hsr).check(hsr, errors);
        new FieldRecaptcha(hsr).check(hsr, errors);
        if (errors.hasFormErrors()) {
            throw new WrongFormException();
        }
    }

    /**
     * Comprueba que el formulario está bien completado y se le envía al usuario
     * el email con el link de validación. Solo si el email se envía correctamente 
     * se crea el usuario en la BD.
     * @param hsr
     * @throws ClassNotFoundException
     * @throws Exception 
     */
    public ModelAndView registerUser() {
        try {
            checkForm();
            sendValidationMail();
            createUser();
            return new ModelAndView(Views.REGISTRATION__REGISTERED);
        } catch (WrongFormException wfE) {
            return new ModelAndView(Views.REGISTRATION);
        } catch (MailNotSentException ex) {
            errors.setLogicError(ex.getMessage());
            return new ModelAndView(Views.REGISTRATION);
        } catch (PasswordEncriptionException ex) {
            errors.setLogicError(ex.getMessage());
            return new ModelAndView(Views.REGISTRATION);
        }
    }
}
