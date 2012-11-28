/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

/**
 * Una vez el usuario rellenó correctamente el formulario de registro se le envía 
 * un email con una clave de validación para su cuenta y el lenguaje elegido en el
 * formulario de registro.
 * Si se envía bien se procede a crear el usuario en la BD. En caso contrario añadimos 
 * el error en el envío a la lista de errores {@link RegistrationService#errors}.
 * @author yomac
 */
public class ValidationMail extends Mailer {
    
    public ValidationMail(String verifKey, String lang, HttpServletRequest hsr) 
            throws UnsupportedEncodingException, MessagingException {
        super(hsr);
        String activationLink = super.baseURL+ "/registration.htm?do=validate&key=";
        BodyPart htmlText = new MimeBodyPart();
        String langURLenc = URLEncoder.encode(lang, "UTF-8");

        //se manda el código de activación junto con el nombre de su lenguaje nativo codificado para la URL
        String msg = "Pulsa " + "<a href=\"" + activationLink + verifKey
                + "&lang=" + langURLenc + "\">aquí</a>"
                + " para activar tu cuenta.";
        htmlText.setContent(msg, "text/html");
        emailContent = new MimeMultipart();
        emailContent.addBodyPart(htmlText);
        super.emailContent = emailContent;
        super.subject = "Link para activar tu cuenta";
    }
}
