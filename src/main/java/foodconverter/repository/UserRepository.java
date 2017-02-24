package foodconverter.repository;

import foodconverter.bindingModel.UserBindingModel;
import foodconverter.entity.PasswordResetToken;
import foodconverter.entity.User;
import foodconverter.error.UserAlreadyExistException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by Iliya on 15.12.2016 г..
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
