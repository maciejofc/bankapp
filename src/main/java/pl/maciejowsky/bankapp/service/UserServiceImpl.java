package pl.maciejowsky.bankapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.NoUserFoundException;
import pl.maciejowsky.bankapp.exceptions.PermissionDeniedException;
import pl.maciejowsky.bankapp.exceptions.SamePasswordException;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;

import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final static int RECORDS_PER_PAGE = 10;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAccountDAO userAccountDAO;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public boolean checkIfUserEmailAlreadyExists(String email) {
        return userDAO.findUserByEmail(email) != null;
    }


    @Override
    public void registerUser(FormRegisterUser formRegisterUser) throws UserAlreadyExistException {
        if (checkIfUserEmailAlreadyExists(formRegisterUser.getEmail())) {
            throw new UserAlreadyExistException("User already exists for this email");
        }

        String userRole = "user";
        String userAuthority = "ACCESS_1";
        formRegisterUser.setPassword(formRegisterUser.getPassword());
        User user = new User(formRegisterUser);
        user.setRoles(userRole);
        user.setAuthorities(userAuthority);

//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        int userId = userDAO.saveUser(user);

        userAccountDAO.saveUserAccount(userId);

    }

    @Override
    public void registerManager(FormRegisterUser formRegisterUser) throws UserAlreadyExistException {

        if (checkIfUserEmailAlreadyExists(formRegisterUser.getEmail())) {
            throw new UserAlreadyExistException("User already exists for this email");
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        formRegisterUser.setPassword(formRegisterUser.getPassword());
        User user = new User(formRegisterUser);
        String userRole = "manager";
        String userAuthority = "ACCESS_1,ACCESS_2";
        user.setRoles(userRole);
        user.setAuthorities(userAuthority);
        user.setUserType(null);
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
        //checking if user is online
        if (userList != null) {
            userList.stream().filter(user -> checkIfUserIsOnline(user.getEmail()))
                    .forEach(user -> user.setOnline(true));

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
    public boolean checkIfUserIsOnline(String username) {
        boolean isOnline = sessionRegistry.getAllPrincipals()
                .stream()
                .filter(loggedUser -> loggedUser instanceof UserDetails)
                .anyMatch(loggedUser -> ((UserDetails) loggedUser).getUsername().equals(username));
        return isOnline;
    }

    @Override
    public User getUserById(Principal principalOfAsker, int requestedUserId) throws PermissionDeniedException, NoUserFoundException {
        User userById = userDAO.findUserById(requestedUserId);
        if (userById == null)
            throw new NoUserFoundException("No such user");
        checkIfRequestAskerHavePermission(principalOfAsker, userById.getEmail());
        return userById;

    }

    @Override
    public User getUserByEmail(String userEmail) throws NoUserFoundException {
        User userByEmail = userDAO.findUserByEmail(userEmail);
        if (userByEmail == null)
            throw new NoUserFoundException("No such user with this email");

        return userByEmail;
    }

    private void checkIfRequestAskerHavePermission(Principal principalOfAsker, String emailOfRequestedUser) throws PermissionDeniedException {
        String adminRolesPermission = "manager,user";
        String managerRolesPermission = "user";
        String roleOfAsker = getUserRoleByUsername(principalOfAsker.getName());
        String roleOfAsked = getUserRoleByUsername(emailOfRequestedUser);
        boolean havePermission = true;

        System.out.println("ROLE OF ASKER IS" + roleOfAsker);
        if (roleOfAsker.equals("admin")) {

            havePermission = adminRolesPermission.contains(roleOfAsked);

        } else if (roleOfAsker.equals("manager")) {
            havePermission = managerRolesPermission.contains(roleOfAsked);
        }
        if (!havePermission) {

            throw new PermissionDeniedException("You have no permission to this data");
        }

    }

//    @Override
//    public User getUserById(Principal principalOfAsker, int userId) {
//        String askerRole = getUserRoleByUsername(principalOfAsker.getName());
//        User foundUser = userDAO.findUserById(userId);
//        String foundUserRole = getUserRoleByUsername(foundUser.getEmail());
//
//        if (askerRole.equals("admin")) {
//            if (foundUserRole.equals("admin")) {
//                return null;
//            }
//            return foundUser;
//        } else {
//            if (foundUserRole.equals("manager") || foundUserRole.equals("admin")) {
//                return null;
//            } else
//                return foundUser;
//        }
//
//    }


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

    @Override
    public void changePassword(int userId, String newPassword) throws SamePasswordException {
        User user = userDAO.findUserById(userId);
        if (user.getPassword().equals(newPassword)) {
            throw new SamePasswordException("This is your current password try another");
        }
        userDAO.updatePassword(userId, newPassword);
    }

    @Override
    public void changeEmail(int userId, String newEmail) throws UserAlreadyExistException {
        if (userDAO.findUserById(userId).getEmail().equals(newEmail)) {
            throw new UserAlreadyExistException("This is your current email, specify another");
        }
        if (checkIfUserEmailAlreadyExists(newEmail)) {
            throw new UserAlreadyExistException("This email is already taken, please try another");
        }
        userDAO.updateEmail(userId, newEmail);
    }
}
