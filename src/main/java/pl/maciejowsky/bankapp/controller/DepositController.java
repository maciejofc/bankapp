package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.exceptions.DepositException;
import pl.maciejowsky.bankapp.model.Deposit;
import pl.maciejowsky.bankapp.service.DepositService;
import pl.maciejowsky.bankapp.service.DepositServiceImpl;

import java.security.Principal;

@Controller
public class DepositController {

    @Autowired
    DepositService depositService;

    @Autowired
    UserAccountDAO userAccountDAO;

    @GetMapping("/deposit")
    public String goToDeposit() {

        return "deposit";
    }

    @GetMapping("/deposit/make")
    public String goToMakeDeposit(@SessionAttribute("loggedInUserId") int userId,
                                  Principal principal, Model model) {
        model.addAttribute("depositVariants", depositService.listDepositVariants(principal));
        model.addAttribute("deposit", new Deposit());
        model.addAttribute("availableFunds", userAccountDAO.getBalanceOfUserByAccountNumber(userAccountDAO.getAccountNumberByUserId(userId)));
        model.addAttribute("activeDeposits", depositService.getActiveDepositsForUser(userId));

        return "make-deposit";
    }

    @PostMapping("/deposit/make")
    public String makeDeposit(@SessionAttribute("loggedInUserAccountNumber") String loggedInUserAccountNumber,
                              @SessionAttribute("loggedInUserId") int userId,
                              @ModelAttribute Deposit deposit,
                              Principal principal,
                              Model model) {

        try {
            depositService.makeDeposit(deposit, loggedInUserAccountNumber);


        } catch (DepositException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("depositVariants", depositService.listDepositVariants(principal));
            model.addAttribute("deposit", new Deposit());
            model.addAttribute("availableFunds", userAccountDAO.getBalanceOfUserByAccountNumber(loggedInUserAccountNumber));
            model.addAttribute("activeDeposits", depositService.getActiveDepositsForUser(userId));

            return "make-deposit";
        }
        return "redirect:/deposit/make?depositSuccess=true";
    }

    @GetMapping("/deposit/complete/{id}")
    public String completeDeposit(@SessionAttribute("loggedInUserAccountNumber") String accountNumber,
                                  @PathVariable("id") int depositId) {

        depositService.completeDeposit(depositId, accountNumber);
        return "redirect:/deposit/make";
    }

    @GetMapping("/deposit/history")
    public String goToDepositHistory(@SessionAttribute("loggedInUserId") int userId, Model model) {
        model.addAttribute("depositHistory", depositService.getDepositHistoryForUser(userId));
        model.addAttribute("moneyEarned",depositService.calculateMoneyAlreadyEarned(userId));
        return "deposit-history";
    }


}
