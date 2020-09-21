package ir.maktab.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Service
@PropertySource("classpath:mail.properties")
public class MailService {

    public static void sendMail(String to, String sub, String msg) throws MessagingException, IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Codebanoo\\IdeaProjects\\online-exam\\src\\main\\resources\\mail.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        properties.put("mail.smtp.host", properties.getProperty("mail.smtp.host"));
        properties.put("mail.smtp.socketFactory.port", properties.getProperty("mail.smtp.socketFactory.port"));
        properties.put("mail.smtp.socketFactory.class", properties.getProperty("mail.smtp.socketFactory.class"));
        properties.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.port", properties.getProperty("mail.smtp.port"));
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("spring.mail.username"),
                        properties.getProperty("spring.mail.password"));
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(sub);
        message.setText(msg);
        Transport.send(message);
    }
//    SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("masoome.aghayari@gmail.com");
//        message.setTo(to);
//        message.setSubject(sub);
//        message.setText(msg);
//        emailSender.send(message);
}
//  @Autowired
//    public static JavaMailSender emailSender;