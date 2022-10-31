package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;
import pl.maciejowsky.bankapp.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"loggedInUserId", "loggedInUserAccountNumber"})
public class UserController {
    private static final List<String> LIST_USER_TYPE = Arrays.asList("REGULAR", "ENTREPRENEUR");
    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserAccountDAO accountDAO;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/save-user-id")
    public String saveIdToSession(Principal principal, Model model) {

        User user = userDAO.findUserByEmail(principal.getName());
        int id = user.getId();
        model.addAttribute("loggedInUserId", id);
        if (user.getRoles().equals("user"))
            model.addAttribute("loggedInUserAccountNumber", accountDAO.getAccountNumberByUserId(id));
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String goToRegisterPage(Model model) {
        model.addAttribute("userForm", new FormRegisterUser());
        model.addAttribute("listUserType", LIST_USER_TYPE);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userForm") FormRegisterUser user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listUserType", LIST_USER_TYPE);
            return "register";
        }
        try {
            userService.registerUser(user);

        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", null, e.getMessage());
            model.addAttribute("userForm", user);
            model.addAttribute("listUserType", LIST_USER_TYPE);
            return "register";
        }
        return "redirect:/login?registerSuccess";
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


}
