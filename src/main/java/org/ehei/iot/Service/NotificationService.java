package org.ehei.iot.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.ehei.iot.Entities.Weather;
import org.ehei.iot.Repository.RoleRepository;
import org.ehei.iot.Repository.UserRepository;
import org.ehei.iot.Repository.WeatherRepository;
import org.ehei.iot.Telegram.WeatherBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService{

    private static final  String EMPLOYE = "employe";
    private static final  String ADMIN = "admin";
    private static final  String DIRECTEUR = "directeur";
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private WeatherBot weatherBot;

    @Override
    public void sendMail(int role,int type,double temp) throws MessagingException {
        String mailto = "";
        switch (role) {
            case 1:
                mailto = userRepository.findByRole(roleRepository.findByRole(EMPLOYE)).getEmail();
                break;
            case 2:
                mailto = userRepository.findByRole(roleRepository.findByRole(ADMIN)).getEmail();
                break;
            case 3:
                mailto = userRepository.findByRole(roleRepository.findByRole(DIRECTEUR)).getEmail();
                break;
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String text = "";
        String types = "";

        switch (type) {
            case 1:
                text = "La température est de retour à la normale.";
                types = "NORMAL";
                break;
            case 2:
                text = "La température est moyenne.";
                types = "MOYEN";
                break;
            case 3:
                text = "La température est dangereuse.";
                types = "DANGER";
                break;
        }

        String htmlMessage = "<html><body>"
                + "<h1 style='color: #FF0000;'>TEMPÉRATURE " + types + "</h1>"
                + "<p>" + text + " : " + temp + "</p>"
                + "<p style='font-style: italic;'>Message from your WEATHER_EHEI</p>"
                + "</body></html>";

        helper.setText(htmlMessage, true); // Set to true to indicate HTML content
        helper.setTo(mailto);
        helper.setSubject("TEMPERATURE " + types);
        helper.setFrom("weatherehei@gmail.com");

        javaMailSender.send(mimeMessage);

        System.out.println("Mail Send...");
    }

    @Override
    public void sendInTelegram(int role,int type,double temp) {
        /*Long chatId = null;
        switch (role) {
            case 1:
                chatId = userRepository.findByRole(roleRepository.findByRole(EMPLOYE)).getTelegramId();
                break;
            case 2:
                chatId = userRepository.findByRole(roleRepository.findByRole(ADMIN)).getTelegramId();
                break;
            case 3:
                chatId = userRepository.findByRole(roleRepository.findByRole(DIRECTEUR)).getTelegramId();
                break;
        }*/
        String text = "";
        String types = "";

        switch (type) {
            case 1:
                text = "La température est de retour à la normale.";
                types = "NORMAL";
                break;
            case 2:
                text = "La température est moyenne.";
                types = "MOYEN";
                break;
            case 3:
                text = "La température est dangereuse.";
                types = "DANGER";
                break;
        }

        text+=types+" ==> "+temp;
        weatherBot.sendMessage(text);

    }

}
