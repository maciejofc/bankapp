package pl.maciejowsky.bankapp.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ViewFormForEditObject {
    @Email(message = "Please provide valid email")
    private String email;

    @Size(min = 8, max = 24, message = "The password must be from 8 to 24 characters")
    private String password;

    public ViewFormForEditObject() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
