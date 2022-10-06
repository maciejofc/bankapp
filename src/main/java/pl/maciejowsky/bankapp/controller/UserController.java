package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private List<String> listUserType = Arrays.asList("REGULAR", "ENTREPRENEUR");
    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;




    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String goToRegisterPage(Model model) {
        model.addAttribute("userForm", new FormRegisterUser());
        model.addAttribute("listUserType", listUserType);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userForm") FormRegisterUser user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listUserType", listUserType);
            return "register";
        }
        try {
            userService.registerUser(user);

        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", null, e.getMessage());
            model.addAttribute("userForm", user);
            model.addAttribute("listUserType", listUserType);
            return "register";
        }
        return "redirect:/login?registerSuccess=true";
    }

    @GetMapping("/login")
    public String goToLoginPage() {
        return "login";
    }

    @RequestMapping("/logout-success")
    public String goToLogoutPage(Model model) {
        model.addAttribute("loggedOut", "You have benn logged out");
        return "index";
    }

    @GetMapping("/users")
    public String getAllUsers(Principal principal, Model model,
                              @RequestParam String orderBy, @RequestParam int page) {

        model.addAttribute("numberOfPages", userService.getNumberOfPages(userDAO.findNumberOfUsers()));
        model.addAttribute("usersList", userService.getAllUsers(principal, orderBy, page));
        model.addAttribute("pageNo", page);
        return "users";
    }

    @GetMapping("/search-user")
    public String getDetailedUserByEmail(Principal principal, Model model,
                                         @RequestParam String email) {
        int userId = userDAO.findUserByEmail(email).getId();
        model.addAttribute("user", userService.getUserById(principal, userId));

        return "user";
    }

    @GetMapping("/user")
    public String getDetailedUser(Model model, @RequestParam int userId) {
        model.addAttribute("user", userService.getUserById(userId));
        return "user";
    }

    @GetMapping("/loggedin-users")
    public String getAllLoggedInUsers(Principal principal, Model model) throws IllegalAccessException {
        model.addAttribute("userSessions", userService.getLoggedInUsers(principal));
        return "loggedin-users";
    }

    @GetMapping("/user/ban")
    public String banUser(Model model, @RequestParam int userId) {
        userService.changeUserLockProperty(userId, true);
        model.addAttribute("user", userService.getUserById(userId));
        return "user";
    }

    @GetMapping("/user/unban")
    public String unBanUser(Model model, @RequestParam int userId) {
        userService.changeUserLockProperty(userId, false);
        model.addAttribute("user", userService.getUserById(userId));
        return "user";
    }


    @RequestMapping("/banned")
    public String goToBannedPage() {
        return "banned-acc";
    }

}
