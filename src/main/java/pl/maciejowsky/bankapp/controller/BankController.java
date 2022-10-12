package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.BankSettings;
import pl.maciejowsky.bankapp.model.BankTransferSettings;
import pl.maciejowsky.bankapp.service.BankService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller()
public class BankController {
    private static final String ERROR_MESSAGE = "Invalid data passed";
    @Autowired
    BankService bankService;

    @ModelAttribute
    public void addSettingsAttributes(Model model) {
        BankSettings bankSettings = bankService.getSettings();
        model.addAttribute("depositSettings", bankSettings.getBankDepositSettings());
        model.addAttribute("transferSettings", bankSettings.getBankTransferSettings());
        model.addAttribute("announcements", bankService.getBankAnnouncements());
        model.addAttribute("announcementForm", new BankAnnouncement());
        model.addAttribute("depositSettingsForm", new BankDepositSettings());
        model.addAttribute("transferSettingsForm", new BankTransferSettings());
    }

    @GetMapping("/bank/settings")
    public String goToGlobalSettings(Model model) {

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


}
