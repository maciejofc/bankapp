package pl.maciejowsky.bankapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;

import java.security.Principal;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final static int RECORDS_PER_PAGE = 10;
    @Autowired
    private UserDAO userDAO;

//    @Autowired
//    PasswordEncoder passwordEncoder;

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
        //if maangaer = users
        //if admin = manager+users
        String role = userDAO.findUserByEmail(principal.getName()).getRoles();
        System.out.println("-----------------------------------------");
        System.out.println(role);
        if (role.equals("admin"))
            userList.stream().filter(user -> user.getRoles().equals("manager") || user.getRoles().equals("user"));
        else if (role.equals("manager")) {
            userList.stream().filter(user -> user.getRoles().equals("users"));
        }
        return userList;
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.findUserById(userId);
    }


    @Override
    public void changeUserLockProperty(int userId, boolean banOrNot) {

        if (banOrNot)
            userDAO.banUser(userId);
        else
            userDAO.unBanUser(userId);
    }
}
