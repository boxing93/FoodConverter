package foodconverter.service;

import foodconverter.bindingModel.ContactUs;
import foodconverter.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Iliya on 2.1.2017 г..
 */

@Service
public class ContactService implements ContactRepository {

    public SimpleMailMessage constructSendMailFromUser(HttpServletRequest httpServletRequest) {
        String message = httpServletRequest.getParameter("message");
        String phone = httpServletRequest.getParameter("phoneNumber");
        String email = httpServletRequest.getParameter("email");
        String name = httpServletRequest.getParameter("name");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("iliyaValchev93@gmail.com");
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setSubject("Contact Message");
        StringBuilder builder = new StringBuilder(64);
        simpleMailMessage.setText(builder.append("Message: ").append(message).append(",from: ").append(name)
        .append(",with email: ").append(email).append(", and phone: ").append(phone).toString());
        return simpleMailMessage;
    }
}
