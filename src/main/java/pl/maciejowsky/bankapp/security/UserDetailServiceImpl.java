package pl.maciejowsky.bankapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.model.User;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String usernameEmail) throws UsernameNotFoundException {
        User user = userDAO.findUserByEmail(usernameEmail);
        if (user == null) {
           throw new UsernameNotFoundException(usernameEmail);
        } else {
            UserPrincipal userPrincipal = new UserPrincipal(user);
            System.out.println(userPrincipal.getUsername());
            System.out.println(userPrincipal.getPassword());

            return userPrincipal;
        }

    }
}
