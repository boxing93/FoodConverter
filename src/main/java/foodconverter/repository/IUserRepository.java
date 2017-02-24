package foodconverter.repository;

import foodconverter.bindingModel.UserBindingModel;
import foodconverter.entity.PasswordResetToken;
import foodconverter.entity.User;
import foodconverter.error.UserAlreadyExistException;

/**
 * Created by Iliya on 28.12.2016 г..
 */
public interface IUserRepository {

    User registerNewUserAccount(UserBindingModel userBindingModel) throws UserAlreadyExistException;

    void saveRegisteredUser(User user);

    PasswordResetToken getVerificationToken(String VerificationToken);

    void createPasswordResetTokenForUser(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    Object findByPassword(User user);
}
