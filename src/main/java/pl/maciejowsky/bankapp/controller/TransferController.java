package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.maciejowsky.bankapp.exceptions.ContactException;
import pl.maciejowsky.bankapp.exceptions.TransferException;
import pl.maciejowsky.bankapp.model.FormTransfer;
import pl.maciejowsky.bankapp.model.TransferFormFilter;
import pl.maciejowsky.bankapp.service.TransferService;

import java.util.Arrays;
import java.util.List;

@Controller
public class TransferController {
    private static final List<String> LIST_TRANSFER_TYPE = Arrays.asList("NORMAL", "EXPRESS", "INSTANT");
    @Autowired
    TransferService transferService;


    @ModelAttribute
    void getUserInformation(@SessionAttribute("loggedInUserAccountNumber") String loggedInUserAccountNumber,
                            @SessionAttribute("loggedInUserId") int userId, Model model) {
        model.addAttribute("formTransfer", new FormTransfer(loggedInUserAccountNumber));
        model.addAttribute("contactsList", transferService.getContactsForUser(userId));
        model.addAttribute("listTransferType", LIST_TRANSFER_TYPE);

    }

    @GetMapping("/transfer")
    public String goToTransfers() {
        return "transfer";
    }

    @GetMapping("/transfer/make")
    public String goToMakeTransfer() {
        return "make-transfer";
    }


    @GetMapping("/transfer/history")
    public String goToTransferHistory(@SessionAttribute("loggedInUserId") int userId,
                                      @ModelAttribute TransferFormFilter transferFormFilter,
                                      Model model
    ) {

        model.addAttribute("transferHistory", transferService.getTransferHistoryForUser(userId, transferFormFilter));
        model.addAttribute("transferFormFilter", transferFormFilter);


        return "transfer-history";
    }


    @PostMapping("/transfer/make")
    public String makeTransfer(@ModelAttribute("formTransfer") FormTransfer formTransfer,

                               Model model) {

        try {
            transferService.makeTransfer(formTransfer);
        } catch (TransferException | ContactException e) {
            model.addAttribute("error", e.getMessage());
            return "make-transfer";
        }
        return "redirect:/transfer/make?transferSuccess";
    }
}
