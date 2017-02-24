package foodconverter.bindingModel;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Created by Iliya on 22.12.2016 г..
 */
public class UserPassword implements Serializable {

    private static final long serialVersionUID = -2221852531645649922L;

    @Length(min = 6)
    private String password;

    @Length(min = 6)
    private String confirmPassword;

    private String verificationToken;

    private int userId;

    private String oldPassword;

    private String newPassword;

    public UserPassword() {
        super();
    }

    public UserPassword(String verificationToken, int userId) {
        this.verificationToken = verificationToken;
        this.userId = userId;
    }

    public UserPassword(String password, String confirmPassword, String verificationToken, int userId) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.userId = userId;
        this.verificationToken = verificationToken;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = password;
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

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
