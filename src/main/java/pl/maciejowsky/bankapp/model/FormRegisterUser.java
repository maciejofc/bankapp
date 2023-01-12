package pl.maciejowsky.bankapp.model;


import org.springframework.format.annotation.DateTimeFormat;
import pl.maciejowsky.bankapp.model.enums.UserType;
import pl.maciejowsky.bankapp.validations.Minimum18yearsConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class FormRegisterUser {
    @NotEmpty(message = "The full name can not be empty")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Minimum18yearsConstraint()
    private LocalDate birthDay;

    @NotEmpty(message = "Email can not be empty")

    @Email(message = "Please provide valid email")
    private String email;

    @Size(min = 8, max = 24, message = "The password must be from 8 to 24 characters")
    private String password;
    @NotNull(message = "Passwords do not match")
    private String confirmPassword;
    private UserType userType;



    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();//check
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        checkPassword();//check
    }

    private void checkPassword() {
        if (this.password == null || this.confirmPassword == null) {
        } else if (!this.password.equals(confirmPassword)) {
            this.confirmPassword = null;
        }
    }

    public FormRegisterUser(String fullName, LocalDate birthDay, String email, String password, String confirmPassword, UserType userType) {
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.userType = userType;
    }

    public FormRegisterUser() {
    }

    public FormRegisterUser(String fullName, LocalDate birthDay, String email, String password, UserType userType) {
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}
