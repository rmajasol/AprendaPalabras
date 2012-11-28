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
public class ChangePassMail extends Mailer {

    public ChangePassMail(String key, HttpServletRequest hsr) throws MessagingException {
        super(hsr);
        String ChPassLink = super.baseURL + "/changePassword.htm?action=changePass&key=";
        BodyPart htmlText = new MimeBodyPart();
        String msg = "Pulsa " + "<a href=\"" + ChPassLink + key + "\">aquí</a>"
                + " para crear una nueva contraseña.<br><br>"
                + "Por motivos de seguridad las contraseñas se guardan encriptadas en "
                + "la base de datos de la aplicación, de manera que en caso de ataque "
                + "al servidor nadie pueda saberlas.";
        htmlText.setContent(msg, "text/html");
        emailContent = new MimeMultipart();
        emailContent.addBodyPart(htmlText);
        super.emailContent = emailContent;
        subject = "Link para cambiar tu contraseña";
    }
}
