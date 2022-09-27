package pl.maciejowsky.bankapp.service;

import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    boolean checkIfUserAlreadyExist(String email);

    void registerUser(FormRegisterUser user) throws UserAlreadyExistException;

    int getNumberOfPages(int rows);

    List<User> getAllUsers(Principal principal, String order, int page);

    void changeUserLockProperty(int userId, boolean banOrNot);

    User getUserById(int userId);


}
