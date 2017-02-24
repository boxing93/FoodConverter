package foodconverter.service;

import foodconverter.bindingModel.UserBindingModel;
import foodconverter.entity.PasswordResetToken;
import foodconverter.entity.User;
import foodconverter.error.UserAlreadyExistException;
import foodconverter.repository.IUserRepository;
import foodconverter.repository.PasswordTokenRepository;
import foodconverter.repository.RoleRepository;
import foodconverter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Iliya on 28.12.2016 г..
 */

@Service
public class UserService implements IUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User registerNewUserAccount(UserBindingModel userBindingModel) throws UserAlreadyExistException {
        if(emailExist(userBindingModel.getEmail())){
            throw new UserAlreadyExistException("There is no user with this email: " + userBindingModel.getEmail());
        }
        User user = new User();
        user.setFullName(userBindingModel.getFullName());
        user.setPassword(userBindingModel.getPassword());
        user.setEmail(userBindingModel.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.saveAndFlush(user);

    }

    @Override
    public PasswordResetToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        PasswordResetToken myToken = new PasswordResetToken(token,user);
        tokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword,user.getPassword());
    }

    @Override
    public Object findByPassword(User user) {
        return user.getPassword();
    }

    public boolean emailExist(String email){
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }
}
