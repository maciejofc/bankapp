package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.maciejowsky.bankapp.exceptions.UserAlreadyExistException;
import pl.maciejowsky.bankapp.model.*;
import pl.maciejowsky.bankapp.service.BankService;
import pl.maciejowsky.bankapp.service.UserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class BankController {
    private final List<String> LIST_ANNOUNCEMENT_VISIBILITY_TYPE = Arrays.asList("MANAGERS", "USERS", "GUESTS", "MEMBERS");
    private static final String ERROR_MESSAGE = "Invalid data passed";
    @Autowired
    BankService bankService;


    @Autowired
    UserService userService;

    @ModelAttribute
    public void getSettingsInformation(Model model) {
        BankSettings bankSettings = bankService.getSettings();

        model.addAttribute("depositSettings", bankSettings.getBankDepositSettings());
        model.addAttribute("transferSettings", bankSettings.getBankTransferSettings());
        model.addAttribute("announcements", bankService.getBankAnnouncements());
        model.addAttribute("announcementForm", new BankAnnouncement());
        model.addAttribute("depositSettingsForm", new BankDepositSettings());
        model.addAttribute("transferSettingsForm", new BankTransferSettings());
        model.addAttribute("listAnnouncementVisibilityType", LIST_ANNOUNCEMENT_VISIBILITY_TYPE);
    }

    @GetMapping("/bank/settings")
    public String goToGlobalSettings() {

        return "bank-settings";
    }

    @PostMapping("/bank/settings/deposit/add")
    public String addDepositVariant(@Valid @ModelAttribute("depositSettingsForm") BankDepositSettings bankDepositSettings,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("depositError", ERROR_MESSAGE);
            return "bank-settings";
        }
        bankService.addDepositVariant(bankDepositSettings);
        return "redirect:/bank/settings";

    }

    @GetMapping("/bank/settings/deposit/delete")
    public String deleteDepositVariant(@RequestParam int depositVariantId) {
        bankService.deleteDepositVariant(depositVariantId);
        return "redirect:/bank/settings";

    }

    @PostMapping("/bank/settings/transfer")
    public String changeTransferSettings(@Valid @ModelAttribute("transferSettingsForm") BankTransferSettings bankTransferSettings,
                                         BindingResult bindingResult,
                                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("transferError", ERROR_MESSAGE);
            return "bank-settings";
        } else {

            bankService.changeTransferSettings(bankTransferSettings);
            return "redirect:/bank/settings";
        }
    }

    @PostMapping("/bank/settings/announcement")
    public String addAnnouncement(@Valid @ModelAttribute("announcementForm") BankAnnouncement bankAnnouncement,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("announcementError", ERROR_MESSAGE);
            return "bank-settings";
        }
        bankService.addAnnouncement(bankAnnouncement);
        return "redirect:/bank/settings";

    }

    @GetMapping("/bank/settings/announcement")
    public String deleteAnnouncement(@RequestParam int announcementId) {

        bankService.deleteAnnouncement(announcementId);
        return "redirect:/bank/settings";
    }


    @GetMapping("/bank/manager/add")
    public String goToAddManager(Model model) {
        model.addAttribute("userForm",new FormRegisterUser());
        return "add-manager";
    }
    @PostMapping("/bank/manager/add")
    public String addManager(@Valid @ModelAttribute("userForm") FormRegisterUser formRegisterUser,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {

            return "add-manager";
        }
        try {
            userService.registerManager(formRegisterUser);
            //1. create account number and save

        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", null, e.getMessage());
            model.addAttribute("userForm", formRegisterUser);

            return "add-manager";
        }
        return "redirect:/bank/manager/add?registerSuccess";
    }

}
