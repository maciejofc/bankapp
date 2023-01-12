package pl.maciejowsky.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.maciejowsky.bankapp.exceptions.RequestException;
import pl.maciejowsky.bankapp.model.Request;
import pl.maciejowsky.bankapp.model.enums.RequestStatus;
import pl.maciejowsky.bankapp.service.RequestService;

@Controller
public class FundRequestController {

    @Autowired
    RequestService requestService;

    @GetMapping("/requests")
    public String goToRequests(Model model) {
        model.addAttribute("requests", requestService.getAllPendingRequests());
        return "requests";
    }

    @GetMapping("/requests/{id}/{decision}")
    public String respondToRequest(@SessionAttribute("loggedInUserId") int userId,
                                   @PathVariable("id") int requestId, @PathVariable String decision
    ) {
        try {
            requestService.changeRequestStatus(requestId, userId, RequestStatus.valueOf(decision.toUpperCase()));
            return "redirect:/requests";
        } catch (RequestException e) {
            return "redirect:/requests?requestError=" + e.getMessage();

        }


    }


    @GetMapping("/request")
    public String goToSendRequest(@SessionAttribute("loggedInUserId") int userId,

                                  Model model) {
        model.addAttribute("userRequests", requestService.getUserRequestsByUserId(userId));
        model.addAttribute("formRequest", new Request());
        return "request";
    }

    @PostMapping("/request")
    public String sendRequest(@SessionAttribute("loggedInUserId") int userId,
                              @ModelAttribute Request request) {
        try {

            requestService.sendRequest(request, userId);
            return "redirect:/request";
        } catch (RequestException e) {
            return "redirect:/request?requestError=" + e.getMessage();

        }

    }


}
