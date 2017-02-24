package foodconverter.repository;

import foodconverter.entity.Recipe;
import foodconverter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by Iliya on 6.1.2017 г..
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Integer>{

    User findByAuthor(Integer id);


}
