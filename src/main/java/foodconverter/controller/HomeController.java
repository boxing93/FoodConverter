package foodconverter.controller;

import foodconverter.bindingModel.RecipeBindingModel;
import foodconverter.entity.Recipe;
import foodconverter.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/**
 * Created by Iliya on 15.12.2016 г..
 */

@Controller
public class HomeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("view", "home/index");
        return "base-layout";
    }

    @GetMapping("/recipes")
    public String recipe(Model model) {
        List<Recipe> recipe = this.recipeRepository.findAll();
        model.addAttribute("recipes", recipe);
        model.addAttribute("view", "article/recipes");
        return "base-layout";
    }


    @GetMapping("/about")
    public String aboutUs(Model model) {
        model.addAttribute("view", "article/about");
        return "base-layout";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        model.addAttribute("view", "user/forgotpassword");
        return "base-layout";
    }

    @GetMapping("/error")
    public String getAnythingElse(Model model) {
        model.addAttribute("view", "error/403");
        return "base-layout";
    }

}
