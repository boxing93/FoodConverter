package foodconverter.service;

import foodconverter.entity.Recipe;
import foodconverter.entity.User;
import foodconverter.repository.RecipeRepository;
import foodconverter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by Iliya on 11.1.2017 г..
 */
@Service
public class RecipeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;


    public User findByAuthor(Recipe recipe){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        user.getId();
        while(user.equals(recipe.getAuthor())){
            recipeRepository.findOne(user.getId());
        }
        return findByAuthor(recipe);
    }
}
