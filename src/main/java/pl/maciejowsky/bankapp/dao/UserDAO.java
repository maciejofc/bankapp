package pl.maciejowsky.bankapp.dao;


import pl.maciejowsky.bankapp.model.User;

import java.util.List;

public interface UserDAO {
    User findUserByEmail(String email);

    User findUserById(int id);

    int saveUser(User user);

    //defult is sorted by creation
    int findNumberOfUsers();

    List<User> findAllUsers(int page);

    void updateEmail(int userId,String newEmail);

    void updatePassword(int userId,String newPassword);

    List<User> findAllUsersAndSortByName(int page);

    List<User> findAllUsersAndSortByAge(int page);

    void banUser(int userId);

    void unBanUser(int userId);


}
