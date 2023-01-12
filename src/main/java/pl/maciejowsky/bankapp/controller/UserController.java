package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowsky.bankapp.dao.BankProfitDAO;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.NoUserFoundException;
import pl.maciejowsky.bankapp.exceptions.PermissionDeniedException;
import pl.maciejowsky.bankapp.exceptions.SamePasswordException;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.*;
import pl.maciejowsky.bankapp.service.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"loggedInUserId", "loggedInUserAccountNumber"})
public class UserController {

    private static final List<String> LIST_USER_TYPE = Arrays.asList("REGULAR", "ENTREPRENEUR");
    @Autowired
    UserService userService;

    @Autowired
    TransferService transferService;
    @Autowired
    DepositService depositService;

    @Autowired
    RequestService requestService;
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserAccountDAO accountDAO;

    @Autowired
    BankService bankService;
    @Autowired
    BankProfitDAO bankProfitDAO;

    @GetMapping("/index")
    public String home(Principal principal, Model model) throws Exception {


        String roleOfUser;
        if (principal != null) {
            User user = userService.getUserByEmail(principal.getName());
            roleOfUser = user.getRoles();
            model.addAttribute("user", user);
            model.addAttribute("bankProfit", bankProfitDAO.getBankProfit());
            model.addAttribute("userAccount", accountDAO.getUserAccountById(user.getId()));
        } else {
            roleOfUser = "guest";

        }
        model.addAttribute("announcements", bankService.getBankAnnouncements(roleOfUser));

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
    public String registerUser(@Valid @ModelAttribute("userForm") FormRegisterUser formRegisterUser,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listUserType", LIST_USER_TYPE);
            return "register";
        }
        try {
            userService.registerUser(formRegisterUser);
            //1. create account number and save

        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", null, e.getMessage());
            model.addAttribute("userForm", formRegisterUser);
            model.addAttribute("listUserType", LIST_USER_TYPE);
            return "register";
        }
        return "redirect:/login?registerSuccess";
    }

    @GetMapping("/profile")
    public String goToMyProfile(Principal principal, Model model) throws Exception {

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userAccount", accountDAO.getUserAccountById(user.getId()));
        return "profile";
    }

    @GetMapping("/profile/edit/{property}")
    public String goToEditProperty(Model model) {
        model.addAttribute("editObject", new ViewFormForEditObject());
        return "edit-property";
    }


    @PostMapping("/profile/edit/{property}")
    public String editProperty(@SessionAttribute("loggedInUserId") int userId,
                               @PathVariable("property") String userProperty,
                               @ModelAttribute("editObject") @Valid ViewFormForEditObject editObject,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {

            return "edit-property";
        }
        if (userProperty.equals("email")) {
            try {
                userService.changeEmail(userId, editObject.getEmail());
            } catch (UserAlreadyExistException e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("editObject", new ViewFormForEditObject());
                return "edit-property";
            }
        } else if (userProperty.equals("password")) {
            try {
                userService.changePassword(userId, editObject.getPassword());

            } catch (SamePasswordException e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("editObject", new ViewFormForEditObject());
                return "edit-property";
            }

        }


        return "redirect:/profile?editSuccess";
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
    public String getDetailedUserByEmail(@RequestParam String email) throws Exception {
        int userId = userService.getUserByEmail(email).getId();
        return "redirect:/user/" + userId;
    }

    @GetMapping("/user/{id}")
    public String getDetailedUser(Principal principal,
                                  Model model, @PathVariable("id") int userId,
                                  @ModelAttribute TransferFormFilter transferFormFilter
    ) throws PermissionDeniedException, NoUserFoundException {
        model.addAttribute("user", userService.getUserById(principal, userId));
        model.addAttribute("yearsOfActivity", transferService.getActiveTransferYears(userId));
        model.addAttribute("chartData", transferService.getChartData(userId));
        model.addAttribute("transferHistory", transferService.getTransferHistoryForUser(userId, transferFormFilter));
        model.addAttribute("transferFormFilter", transferFormFilter);
        model.addAttribute("depositHistory", depositService.getDepositHistoryForUser(userId));
        model.addAttribute("moneyEarned", depositService.calculateMoneyAlreadyEarned(userId));
        model.addAttribute("userAccount", accountDAO.getUserAccountById(userId));
        return "user";
    }


    @GetMapping("/user/ban")
    public String banUser(Model model, @RequestParam int userId) {
        userService.changeUserLockProperty(userId, true);
        model.addAttribute("user", userDAO.findUserById(userId));
        return "redirect:/user/"+userId;
    }

    @GetMapping("/user/unban")
    public String unBanUser(Model model, @RequestParam int userId) {
        userService.changeUserLockProperty(userId, false);
        model.addAttribute("user", userDAO.findUserById(userId));
        return "redirect:/user/"+userId;
    }


}
