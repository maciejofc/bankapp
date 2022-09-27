package pl.maciejowsky.bankapp.dao;


import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;

import java.util.List;

public interface UserDAO {
    User findUserByEmail(String email);

    User findUserById(int id);

    void saveUser(FormRegisterUser user);

    //defult is sorted by creation
    int findNumberOfUsers();

    List<User> findAllUsers(int page);


    List<User> findAllUsersAndSortByName(int page);

    List<User> findAllUsersAndSortByAge(int page);

    void banUser(int userId);

    void unBanUser(int userId);
}
