///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package mail;
//
//import java.security.NoSuchProviderException;
//import java.util.Properties;
//import javax.mail.Folder;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Store;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
///**
// *
// * @author yomac
// */
//public class ProxyMailer {
//
//    protected String SMTP_server = "smtp.gmail.com";
//    protected String SMTP_port = "587";
//    protected String SMTP_user = "aprendapalabras@gmail.com";
//    protected String SMTP_pass = "334qevov7im";
//    protected String from = "AprendaPalabras.com";
//    protected String to = "tishobueno@gmail.com";
//    protected String subject;
//    protected String baseURL = "http://localhost:8080/AprendaPalabras/";
//    protected Properties props;
//    protected Session session;
//    protected MimeMultipart emailContent;
//    protected MimeMessage message;
//
//    public void setEmailContent() {
//    }
//
//    public void send(String dest) throws MessagingException {
//        props = new Properties();
////        props.put("mail.smtp.host", SMTP_server);
////        props.setProperty("mail.smtp.starttls.enable", "true");
////        props.setProperty("mail.smtp.port", SMTP_port);
////        props.setProperty("mail.smtp.user", from);
////        props.setProperty("mail.smtp.auth", "true");
////        session = Session.getDefaultInstance(props, null);
////        session.setDebug(true);
////        message = new MimeMessage(session);
////        message.setFrom(new InternetAddress(from));
////        message.addRecipient(
////                Message.RecipientType.TO,
////                new InternetAddress(dest));
////        message.setSubject(subject);
////        message.setContent(emailContent);
////        Transport t = session.getTransport("smtp");
////        t.connect(SMTP_user, SMTP_pass);
////        t.sendMessage(message, message.getAllRecipients());
////        t.close();
//
//        /** 
//         * Envío sobre SSL 
//         * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
//         */
////        props.put("mail.smtp.host", "smtp.gmail.com");
////        props.put("mail.smtp.socketFactory.port", "25");//465
////        props.put("mail.smtp.socketFactory.class",
////                "javax.net.ssl.SSLSocketFactory");
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.port", "25");
////
////        session = Session.getDefaultInstance(props,
////                new javax.mail.Authenticator() {
////
////                    @Override
////                    protected PasswordAuthentication getPasswordAuthentication() {
////                        return new PasswordAuthentication("aprendapalabras@gmail.com", "334qevov7im");
////                    }
////                });
////
////        try {
////
////            message = new MimeMessage(session);
////            message.setFrom(new InternetAddress("aprendapalabras@gmail.com"));
////            message.setRecipients(Message.RecipientType.TO,
////                    InternetAddress.parse(dest));
//////            message.setSubject("Testing Subject");
//////            message.setText("Dear Mail Crawler,"
//////                    + "\n\n No spam to my email, please!");
////
////            Transport.send(message);
////
////            System.out.println("Done");
////
////        } catch (MessagingException e) {
////            throw new RuntimeException(e);
////        }
//        /**
//         * IMAP
//         */
////        Properties props = System.getProperties();
//        props.setProperty("mail.store.protocol", "imaps");
//        try {
//            Session session = Session.getDefaultInstance(props, null);
//            Store store = session.getStore("imaps");
//            store.connect("imap.gmail.com", "aprendapalabas", "334qevov7im");
//            System.out.println(store);
//
//            Folder inbox = store.getFolder("Inbox");
//            inbox.open(Folder.READ_ONLY);
//            Message messages[] = inbox.getMessages();
//            for (Message message : messages) {
//                System.out.println(message);
//            }
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//            System.exit(1);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            System.exit(2);
//        }
//    }
//}
