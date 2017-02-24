package foodconverter.controller;

import foodconverter.bindingModel.ContactUs;
import foodconverter.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Iliya on 2.1.2017 г..
 */

@Controller
public class ContactControler extends ContactUs{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ContactUs contactUs;

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contact")
    public String contactUs(Model model) {
        model.addAttribute("view", "article/contact");
        return "base-layout";
    }

    @PostMapping("/contact")
    public String contact(@RequestParam("email") String email, Model model, Locale locale,HttpServletRequest request) {
        String name = request.getParameter("name");
        String emails = request.getParameter("email");
        String phone = request.getParameter("phoneNumber");
        String messages = request.getParameter("message");
        if (name.isEmpty() || emails.isEmpty() || phone.isEmpty() || messages.isEmpty()) {
            String message = messageSource.getMessage("message.emptyFields", null, locale);
            model.addAttribute("message", message);
            model.addAttribute("view", "article/contact");
            return "base-layout";
        }
        SimpleMailMessage mail = contactRepository.constructSendMailFromUser(request);
        mailSender.send(mail);
        return "redirect:/contact.html?lang=" + locale.getLanguage();
    }
}
