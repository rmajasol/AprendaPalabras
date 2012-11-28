package service;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import exceptions.LinkAlreadySentException;
import mail.ChangePassMail;
import data.dao.UserDAO;
import exceptions.WrongFormException;
import data.dao.AccessKeyDAO;
import data.model.AccessKey;
import javax.servlet.http.HttpServletRequest;
import data.model.User;
import form.singleFields.FieldEmail;
import form.singleFields.FieldRecaptcha;
import mail.LoginDataMail;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.criterion.Restrictions;
import utils.Encripter;
import error.ErrorMsgs;
import form.repeatedFields.FieldPasswordRep;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yomac
 */
public class ChangePasswordService extends ServiceHelper {

    public ChangePasswordService(HttpServletRequest hsr, HttpServletResponse hsr1) {
        super(hsr, hsr1);
    }

    /**
     * Si no hay errores en el formulario creamos la clave para 
     * crear una contraseña nueva y se manda al usuario en un link a su email.
     */
    public void sendLink(HttpServletRequest hsr) {
        try {
            checkForm_for_sendLink();
            String email = hsr.getParameter("email");
            User u = (User) new UserDAO().findUniqueByCriteria(Restrictions.eq("email", email));

            //miro si al usuario ya se le concedió la clave de paso
            AccessKey ak = new AccessKeyDAO().findUniqueByCriteria(
                    Restrictions.eq("userId", u.getId()));
            if (ak != null) {
                throw new LinkAlreadySentException(ErrorMsgs.ACCESS_KEY_ALREADY_CREATED);
            }

            //se crea la clave
            String key = RandomStringUtils.randomAlphanumeric(80);
            //se envía al email indicado
            new ChangePassMail(key, hsr).send();
            //si se ha enviado bien el email se registra la clave para el usuario en la BD
            AccessKey aKey = new AccessKey(u.getId(), key);
            new AccessKeyDAO().makePersistent(aKey);
        } catch (WrongFormException ex) {
        } catch (LinkAlreadySentException ex) {
            String msg = ex.getMessage() + "<a href=\"changePassword.htm?action=resend&email="
                    + hsr.getParameter("email") + "\">Volver a enviar</a>";
            errors.setLogicError(msg);
        } catch (Exception ex) {
            errors.setLogicError(ex.getMessage());
        }
    }

    public void changePassword() {
        try {
            checkForm_for_changePassword();
            AccessKey ak = new AccessKeyDAO().getAccessKey(hsr.getParameter("key"));
            User u = new UserDAO().getUser(ak);
            String password = hsr.getParameter("password");

            //si se ha marcado la casilla se envían los datos de acceso al email del usuario
            if (hsr.getParameter("send_data_checkbox") != null) {
                LoginDataMail ldm = new LoginDataMail(u.getUsername(), password, hsr);
                ldm.send(u.getEmail());
            }

            //se cambia la contraseña
            String passEnc = Encripter.encPassword(hsr.getParameter("password"));
            u.setPassword(passEnc);
            new UserDAO().makePersistent(u);

            //eliminamos la clave de acceso de la BD si todo ha ido bien
            new AccessKeyDAO().makeTransient(ak);
            
            hsr1.sendRedirect("index.htm");
        } catch (WrongFormException ex) {
        } catch (Exception ex) {
            errors.setLogicError(ex.getMessage());
        }
    }

    public void checkForm_for_sendLink() throws WrongFormException {
        new FieldEmail(hsr).checkToRead(hsr, errors);
        new FieldRecaptcha(hsr).check(hsr, errors);
        if (errors.hasFormErrors()) {
            throw new WrongFormException();
        }
    }

    public void checkForm_for_changePassword() throws WrongFormException {
        new FieldPasswordRep(hsr).check(hsr, errors);
        new FieldRecaptcha(hsr).check(hsr, errors);
        if (errors.hasFormErrors()) {
            throw new WrongFormException();
        }
    }

    public void checkForm_for_resendLink() throws WrongFormException {
        new FieldRecaptcha(hsr).check(hsr, errors);
        if (errors.hasFormErrors()) {
            throw new WrongFormException();
        }
    }

    public void resendLink() {
        try {
            checkForm_for_resendLink();
            String email = hsr.getParameter("email");
            User u = (User) new UserDAO().findUniqueByCriteria(
                    Restrictions.eq("email", email));
            AccessKey ak = new AccessKeyDAO().findUniqueByCriteria(
                    Restrictions.eq("userId", u.getId()));
            ChangePassMail cpm = new ChangePassMail(ak.getAccessKey(), hsr);
            cpm.send(email);
        } catch (WrongFormException ex) {
        } catch (Exception ex) {
            errors.setLogicError(ex.getMessage());
        }
    }
}