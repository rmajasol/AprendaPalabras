/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

/**
 * Envía un email con la clave para poder cambiar la contraseña del usuario.
 * @author yomac
 */
public class LoginDataMail extends Mailer {

    public LoginDataMail(String username, String password, HttpServletRequest hsr) throws MessagingException {
        super(hsr);
        BodyPart htmlText = new MimeBodyPart();
        String msg = "Sus datos de acceso son:<br>" + "<b>Nombre de usuario:</b> " 
                + username + "<br><b>Contraseña:</b> " + password;
        htmlText.setContent(msg, "text/html");
        emailContent = new MimeMultipart();
        emailContent.addBodyPart(htmlText);
        super.emailContent = emailContent;
        subject = "Datos de acceso a \"AprendaPalabras\"";
    }
}
