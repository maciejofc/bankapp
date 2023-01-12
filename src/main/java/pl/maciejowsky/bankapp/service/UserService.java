package pl.maciejowsky.bankapp.service;

import pl.maciejowsky.bankapp.exceptions.NoUserFoundException;
import pl.maciejowsky.bankapp.exceptions.PermissionDeniedException;
import pl.maciejowsky.bankapp.exceptions.SamePasswordException;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    boolean checkIfUserEmailAlreadyExists(String email);

    String getUserRoleByUsername(String email);

    void registerUser(FormRegisterUser user) throws UserAlreadyExistException;

    void registerManager(FormRegisterUser formRegisterUser) throws UserAlreadyExistException;

    int getNumberOfPages(int rows);

    List<User> getAllUsers(Principal principal, String order, int page);

    boolean checkIfUserIsOnline(String username);

    User getUserByEmail(String userEmail) throws Exception;

    void changeUserLockProperty(int userId, boolean banOrNot);

    void changePassword(int userId, String newPassword) throws SamePasswordException;

    void changeEmail(int userId, String newEmail) throws UserAlreadyExistException;

    User getUserById(Principal principalOfAsker, int requestedUserId) throws PermissionDeniedException, NoUserFoundException;


}
