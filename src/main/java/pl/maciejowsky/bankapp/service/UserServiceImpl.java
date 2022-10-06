package pl.maciejowsky.bankapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final static int RECORDS_PER_PAGE = 10;
    @Autowired
    private UserDAO userDAO;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public boolean checkIfUserAlreadyExist(String email) {
        return userDAO.findUserByEmail(email) != null ? true : false;
    }



    @Override
    public void registerUser(FormRegisterUser user) throws UserAlreadyExistException {
        if (checkIfUserAlreadyExist(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists for this email");

        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        userDAO.saveUser(user);


    }

    @Override
    public int getNumberOfPages(int rows) {
        return (int) Math.ceil((double) rows / RECORDS_PER_PAGE);
    }

    @Override
    public List<User> getAllUsers(Principal principal, String order, int page) {
        List<User> userList;
        switch (order) {
            case "creation":
                userList = userDAO.findAllUsers(page);
                break;

            case "name":
                userList = userDAO.findAllUsersAndSortByName(page);
                break;
            case "age":
                userList = userDAO.findAllUsersAndSortByAge(page);
                break;
            default:
                userList = null;
        }

        String role = getUserRoleByUsername(principal.getName());
        if (role.equals("admin"))
            userList.stream().filter(user -> user.getRoles().equals("manager") || user.getRoles().equals("user"));
        else if (role.equals("manager")) {
            userList.stream().filter(user -> user.getRoles().equals("users"));
        }
        return userList;
    }

    public String getUserRoleByUsername(String email) {
        return userDAO.findUserByEmail(email).getRoles();
    }

    @Override
    public List<UserDetails> getLoggedInUsers(Principal principalOfAsker) {
        List<UserDetails> sessions = sessionRegistry.getAllPrincipals()
                .stream()
                .filter(loggedUser -> loggedUser instanceof UserDetails)
                .map(UserDetails.class::cast)
                .collect(Collectors.toList());
        String role = getUserRoleByUsername(principalOfAsker.getName());
        if (role.equals("admin"))
            sessions.stream().filter(loggedUser -> loggedUser.getAuthorities().contains("ROLE_MANAGER") || loggedUser.getAuthorities().contains("ROLE_USER"));
        else if (role.equals("manager")) {
            sessions.stream().filter(loggedUser -> loggedUser.getAuthorities().contains("ROLE_USER"));
        }
        return sessions;
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.findUserById(userId);

    }

    @Override
    public User getUserById(Principal principalOfAsker, int userId) {
        String askerRole = getUserRoleByUsername(principalOfAsker.getName());
        User foundUser = userDAO.findUserById(userId);
        String foundUserRole = getUserRoleByUsername(foundUser.getEmail());

        if (askerRole.equals("admin")) {
            if(foundUserRole.equals("admin")){
                return null;
            }
            return foundUser;
        } else {
            if (foundUserRole.equals("manager") || foundUserRole.equals("admin")) {
                return null;
            } else
                return foundUser;
        }

    }


    //This method also invalidates session!
    @Override
    public void changeUserLockProperty(int userId, boolean banOrNot) {

        if (banOrNot) {
            String userEmail = userDAO.findUserById(userId).getEmail();
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(userEmail, false);
            if (sessions != null) {
                sessions.forEach(sessionInformation -> sessionInformation.expireNow());
            }
            userDAO.banUser(userId);
        } else
            userDAO.unBanUser(userId);
    }
}
