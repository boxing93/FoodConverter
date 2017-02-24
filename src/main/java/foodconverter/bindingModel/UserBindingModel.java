package foodconverter.bindingModel;

import foodconverter.repository.EmailValidatorRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by Iliya on 15.12.2016 г..
 */


public class UserBindingModel {

    @EmailValidatorRepository
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    @Length(min = 6)
    private String password;
    @NotNull
    @Length(min = 6)
    private String confirmPassword;

    public UserBindingModel() {
    }

    public UserBindingModel(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
