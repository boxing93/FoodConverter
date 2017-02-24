package foodconverter.repository;

import foodconverter.bindingModel.ContactUs;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Iliya on 2.1.2017 г..
 */
public interface ContactRepository {

   SimpleMailMessage constructSendMailFromUser(HttpServletRequest httpServletRequest);

}
