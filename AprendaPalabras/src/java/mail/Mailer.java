/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import exceptions.MailNotSentException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import error.ErrorMsgs;
import javax.servlet.http.HttpServletRequest;
import utils.Common;

/**
 *
 * @author yomac
 */
public class Mailer {
    
    protected HttpServletRequest hsr;
    protected String SMTP_server = "smtp.gmail.com";
    protected String SMTP_port = "587";
    protected String SMTP_user = "aprendapalabras@gmail.com";
    protected String SMTP_pass = "334qevov7im";
    protected String from = "AprendaPalabras.com";
    protected String to = "tishobueno@gmail.com";
    protected String subject;
//    protected String baseURL = "http://localhost:8080/AprendaPalabras/";
    protected String baseURL;
    protected Properties props;
    protected Session session;
    protected MimeMultipart emailContent;
    protected MimeMessage message;
    
    public Mailer(HttpServletRequest hsr){
        this.hsr = hsr;
        this.baseURL = Common.getBaseUrl(hsr);
    }

    public void setEmailContent() {
    }

    public void send() throws MailNotSentException {
        try {
            props = new Properties();
            props.put("mail.smtp.host", SMTP_server);
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", SMTP_port);
            props.setProperty("mail.smtp.user", from);
            props.setProperty("mail.smtp.auth", "true");
            session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(hsr.getParameter("email")));
            message.setSubject(subject);
            message.setContent(emailContent);
            Transport t = session.getTransport("smtp");
            t.connect(SMTP_user, SMTP_pass);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception ex) {
            throw new MailNotSentException(ErrorMsgs.FAILURE_SENDING_EMAIL);
        }
    }
    
    public void send(String dest) throws MailNotSentException {
        try {
            props = new Properties();
            props.put("mail.smtp.host", SMTP_server);
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", SMTP_port);
            props.setProperty("mail.smtp.user", from);
            props.setProperty("mail.smtp.auth", "true");
            session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(dest));
            message.setSubject(subject);
            message.setContent(emailContent);
            Transport t = session.getTransport("smtp");
            t.connect(SMTP_user, SMTP_pass);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception ex) {
            throw new MailNotSentException(ErrorMsgs.FAILURE_SENDING_EMAIL);
        }
    }
}
