package foodconverter.controller;

import foodconverter.bindingModel.UserBindingModel;
import foodconverter.bindingModel.UserPassword;
import foodconverter.entity.Role;
import foodconverter.entity.User;
import foodconverter.repository.IUserRepository;
import foodconverter.repository.RoleRepository;
import foodconverter.repository.UserRepository;
import org.codehaus.groovy.tools.shell.util.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Iliya on 15.12.2016 г..
 */

@Controller
public class UserController {

    @Autowired
    org.springframework.context.MessageSource messageSource;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");
        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel, Locale locale,Model model, HttpServletRequest request) {
        if(!(userRepository.findByEmail(request.getParameter("email")) == null)){
            String message = messageSource.getMessage("message.regError", null, locale);
            model.addAttribute("view","user/register");
            model.addAttribute("message", message);
           return "base-layout";
        }
         if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
            String message = messageSource.getMessage("PasswordMatches.user", null, locale);
            model.addAttribute("view","user/register");
            model.addAttribute("message", message);
            return "base-layout";
        }else if(userBindingModel.getEmail().isEmpty() || userBindingModel.getFullName().isEmpty() || userBindingModel.getPassword().isEmpty()
                || userBindingModel.getConfirmPassword().isEmpty()){
            String message = messageSource.getMessage("message.emptyFields", null,locale);
            model.addAttribute("view","user/register");
            model.addAttribute("message", message);
            return "base-layout";
        }else if(!userBindingModel.getEmail().contains("@")){
            String message = messageSource.getMessage("message.badEmail", null,locale);
            model.addAttribute("view","user/register");
            model.addAttribute("message", message);
            return "base-layout";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        this.userRepository.saveAndFlush(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");
        return "base-layout";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";

    }

    @GetMapping("/successLogin")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model, UserBindingModel userBindingModel, HttpServletRequest request, Locale locale) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = this.userRepository.findByEmail(principal.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("view", "user/successLogin");
        return "base-layout";
    }

    @GetMapping("/addRecipe")
    @PreAuthorize("isAuthenticated()")
    public String addRecipe(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = this.userRepository.findByEmail(principal.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("view", "user/addRecipe");
        return "base-layout";
    }

    @GetMapping("/changePasswordFromAccount")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordFromAccount(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("view", "user/changePasswordFromAccount");
        return "base-layout";
    }

    @PostMapping("/savePasswordFromAccount")
    @PreAuthorize("isAuthenticated()")
    public String savePasswordFromAccount(UserBindingModel userBindingModel) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        user.setPassword(userBindingModel.getPassword());
        user.setPassword(passwordEncoder.encode(userBindingModel.getPassword()));
        userRepository.save(user);
        return "redirect:/successLogin";
    }

}
