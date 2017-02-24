package foodconverter.controller;

import foodconverter.bindingModel.RecipeBindingModel;
import foodconverter.entity.Recipe;
import foodconverter.entity.User;
import foodconverter.repository.RecipeRepository;
import foodconverter.repository.UserRepository;
import foodconverter.service.RecipeService;
import foodconverter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Iliya on 6.1.2017 г..
 */

@Controller
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("view", "user/viewMyRecipes");
        return "base-layout";
    }

    @PostMapping("/user/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(RecipeBindingModel recipeBindingModel) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(userDetails.getUsername());
        Recipe recipe = new Recipe(
                recipeBindingModel.getProducts(),
                recipeBindingModel.getPrice(),
                recipeBindingModel.getSummary(),
                recipeBindingModel.getTitle(),
                userEntity
        );
        this.recipeRepository.saveAndFlush(recipe);
        return "redirect:/user/create";
    }

    @GetMapping("/user/viewMyRecipes")
    @PreAuthorize("isAuthenticated()")
    public String showUserRecipes(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userRepository.findByEmail(userDetails.getUsername());
        List<Recipe> recipe = recipeRepository.findAll();
        model.addAttribute("userRecipe", recipe);
        model.addAttribute("user", userEntity);
        model.addAttribute("view", "user/viewMyRecipes");
        return "base-layout";
    }

    @GetMapping("/recipe/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.recipeRepository.exists(id)) {
            return "redirect:/";
        }
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userEntity = this.userRepository.findByEmail(principal.getUsername());
            model.addAttribute("user", userEntity);
        }
        Recipe recipe = this.recipeRepository.findOne(id);
        model.addAttribute("userRecipe", recipe);
        model.addAttribute("view","user/details");
        model.addAttribute("view", "user/detailsLogin");

        return "base-layout";
    }

    @GetMapping("/user/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.recipeRepository.exists(id)) {
            return "redirect:/";
        }
        Recipe recipe = this.recipeRepository.findOne(id);

        if (!isUserAuthorOrAdmin(recipe)) {
            return "redirect:/user/" + id;
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user",user);
        model.addAttribute("view", "user/editRecipe");
        model.addAttribute("userRecipe", recipe);
        return "base-layout";
    }

    @PostMapping("/user/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, RecipeBindingModel recipeBindingModel) {
        if (!this.recipeRepository.exists(id)) {
            return "redirect:/";
        }
        Recipe recipe = this.recipeRepository.findOne(id);

        if (!isUserAuthorOrAdmin(recipe)) {
            return "redirect:/user/" + id;
        }

        recipe.setSummary(recipeBindingModel.getSummary());
        recipe.setTitle(recipeBindingModel.getTitle());
        recipe.setPrice(recipeBindingModel.getPrice());
        this.recipeRepository.saveAndFlush(recipe);
        return "redirect:/recipe/" + recipe.getId();
    }

    @GetMapping("/user/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.recipeRepository.exists(id)) {
            return "redirect:/";
        }
        Recipe recipe = this.recipeRepository.findOne(id);

        if (!isUserAuthorOrAdmin(recipe)) {
            return "redirect:/user/" + id;
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user",user);
        model.addAttribute("userRecipe", recipe);
        model.addAttribute("view", "user/deleteRecipe");
        return "base-layout";
    }

    @PostMapping("/user/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.recipeRepository.exists(id)) {
            return "redirect:/";
        }

        Recipe recipe = this.recipeRepository.findOne(id);

        if (!isUserAuthorOrAdmin(recipe)) {
            return "redirect:/user/" + id;
        }

        this.recipeRepository.delete(recipe);
        return "redirect:/user/viewMyRecipes";
    }

    private boolean isUserAuthorOrAdmin(Recipe recipe) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(userDetails.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(recipe);
    }
}
