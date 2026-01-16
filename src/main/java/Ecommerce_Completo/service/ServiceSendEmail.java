package Ecommerce_Completo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ServiceSendEmail {

    private String userName = "apit50904@gmail.com";
    private String senha = "xfeoddriqfdhoakd"; // estou deixando dessa maneira apenas para fins didáticos. Depois que eu terminar o projeto, vou apagar a conta.
                                                    // Correto Seria utilizar variáveis de ambiente ou um cofre de segredos.

    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino)
            throws UnsupportedEncodingException, MessagingException {

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        properties.put("mail.smtp.connectiontimeout", "10000");
        properties.put("mail.smtp.timeout", "10000");
        properties.put("mail.smtp.writetimeout", "10000");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, senha);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, "Natanael", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
        message.setSubject(assunto);
        message.setContent(mensagem, "text/html; charset=UTF-8");

        Transport.send(message);
    }
}
