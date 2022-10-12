package pl.maciejowsky.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransferController {

    @GetMapping("/transfers")
    public String goToTransfers(Model model) {
        return "transfers";
    }
@GetMapping("/transfers/make")
    public String goToMakeTransfer(){
        return "make-transfer.html";
    }
}
