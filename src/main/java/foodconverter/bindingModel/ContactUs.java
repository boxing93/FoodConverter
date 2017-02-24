package foodconverter.bindingModel;

import foodconverter.repository.EmailValidatorRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Iliya on 22.12.2016 г..
 */
public class ContactUs {

    @NotNull
    private String name;

    @NotNull
    @EmailValidatorRepository
    private String email;

    @NotNull
    @Size(min = 0, max = 15)
    private int phoneNumber;

    @NotNull
    @Size(min= 10, max = 150)
    private String message;

    public ContactUs(String name, String email, int phoneNumber, String message) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public ContactUs() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
