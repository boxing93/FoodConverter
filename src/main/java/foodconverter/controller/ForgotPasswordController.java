package foodconverter.controller;

import foodconverter.bindingModel.UserPassword;
import foodconverter.entity.PasswordResetToken;
import foodconverter.entity.User;
import foodconverter.repository.IUserRepository;
import foodconverter.repository.UserRepository;
import foodconverter.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by Iliya on 23.12.2016 г..
 */

@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.core.env.Environment env;

    @Autowired
    private MessageSource messages;

    @Autowired
    private IUserRepository iUserRepository;

    @PostMapping("/resetPassword")
    public String resetPassword(
            HttpServletRequest request,Model model, @RequestParam("email") String email, RedirectAttributes redirectAttributes, Locale locale, SessionStatus sessionStatus) {
        User user = userRepository.findByEmail(email);
        String userEmail = request.getParameter("email");
        if(userEmail.isEmpty()){
            String message = messages.getMessage("message.emptyFields", null,locale);
            model.addAttribute("message",message);
            model.addAttribute("view", "user/forgotPassword");
            return "base-layout";
        }
        String token = UUID.randomUUID().toString();
        iUserRepository.createPasswordResetTokenForUser(user, token);
        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();
        SimpleMailMessage mail = constructResetTokenEmail(appUrl, request.getLocale(), token, user);
        mailSender.send(mail);
        String message = messages.getMessage("message.resetPasswordEmail", null, locale);
        model.addAttribute("message", message);
        model.addAttribute("view", "user/forgotPassword");
        return "base-layout";

    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(User user,
                                         Locale locale, Model model, @RequestParam("id") long id, @RequestParam("token") String token) {
        PasswordResetToken passToken = iUserRepository.getPasswordResetToken(token);
        user = passToken.getUser();
        if (passToken == null || user.getId() != id) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/login.html?lang=" + locale.getLanguage();
        }
        Calendar calendar = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            return "redirect:/login.html?lang=" + locale.getLanguage();
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    @GetMapping("/updatePassword")
    public String updatePassword(Model model) {
        model.addAttribute("view", "user/changePassword");
        return "base-layout";
    }

    @PostMapping("/savePassword")
    public String savePassword(@Valid UserPassword userPassword) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        iUserRepository.changeUserPassword(user, userPassword.getNewPassword());
        return "redirect:/login";
    }


    private final SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Reset Password");
        email.setText(message + " \r\n" + url);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }


}

