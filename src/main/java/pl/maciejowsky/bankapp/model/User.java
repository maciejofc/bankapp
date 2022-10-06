package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.UserType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User implements Serializable {

    private int id;

    private String fullName;

    private LocalDate birthDay;
    private String email;

    private String password;

    private UserType userType;
    private String createdAt;
    private String updatedAt;

    private String authorities;
    private String roles;
    private boolean isLocked;

    public User(int id, String fullName, LocalDate birthDay, String email, String password, UserType userType, String createdAt, String updatedAt, String authorities, String roles, boolean isLocked) {
        this.id = id;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorities = authorities;
        this.roles = roles;
        this.isLocked = isLocked;
    }

    public User() {
    }

    public List<String> getRolesList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getAuthoritiesList() {
        if (this.authorities.length() > 0) {
            return Arrays.asList(this.authorities.split(","));
        }
        return new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (isLocked() != user.isLocked()) return false;
        if (!getFullName().equals(user.getFullName())) return false;
        if (!getBirthDay().equals(user.getBirthDay())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (getUserType() != user.getUserType()) return false;
        if (!getCreatedAt().equals(user.getCreatedAt())) return false;
        if (getUpdatedAt() != null ? !getUpdatedAt().equals(user.getUpdatedAt()) : user.getUpdatedAt() != null)
            return false;
        if (!getAuthorities().equals(user.getAuthorities())) return false;
        return getRoles().equals(user.getRoles());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getBirthDay().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (getUserType() != null ? getUserType().hashCode() : 0);
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + (getUpdatedAt() != null ? getUpdatedAt().hashCode() : 0);
        result = 31 * result + getAuthorities().hashCode();
        result = 31 * result + getRoles().hashCode();
        result = 31 * result + (isLocked() ? 1 : 0);
        return result;
    }
}
