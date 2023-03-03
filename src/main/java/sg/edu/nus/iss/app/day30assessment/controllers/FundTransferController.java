package sg.edu.nus.iss.app.day30assessment.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.day30assessment.models.Account;
import sg.edu.nus.iss.app.day30assessment.models.TransferDetails;
import sg.edu.nus.iss.app.day30assessment.repositories.AccountsRepository;

@Controller
public class FundTransferController {
    
    @Autowired
    private AccountsRepository accountRepository;

    private Logger logger = Logger.getLogger(FundTransferController.class.getName());

    @GetMapping(path={"/", "/index.html"})
    public String showTransferForm(Model model, HttpSession session) {
        session.invalidate();
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        model.addAttribute("transferDetails", new TransferDetails());
        return "index";
    }

    @PostMapping(path="/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String transferFunds(@ModelAttribute("transferDetails") @Valid TransferDetails transferDetails,
                                BindingResult result, Model model) {
    

        // Check for validation errors
        if (result.hasErrors()) {
            return "index";
        }

        // Check if both accounts exist
        Account fromAccount = accountRepository.findByAccountId(transferDetails.getFromAccount());
        Account toAccount = accountRepository.findByAccountId(transferDetails.getToAccount());
        if (fromAccount == null || toAccount == null) {
            model.addAttribute("errorMessage", "One or both accounts do not exist.");
            return "index";
        }

        // Check if accounts are different
        if (fromAccount.getAccountId().equals(toAccount.getAccountId())) {
            model.addAttribute("errorMessage", "Cannot transfer funds to the same account.");
            return "index";
        }

        // Check if transfer amount is valid
        if (transferDetails.getTransferAmount().compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("errorMessage", "Transfer amount must be greater than zero.");
            return "index";
        }

        if (transferDetails.getTransferAmount().compareTo(BigDecimal.TEN) < 0) {
            model.addAttribute("errorMessage", "Minimum transfer amount is $10.");
            return "index";
        }

        // Check if from account has sufficient balance
        if (fromAccount.getBalance().compareTo(transferDetails.getTransferAmount()) < 0) {
            model.addAttribute("errorMessage", "Insufficient funds.");
            return "index";
        }

        // Perform transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDetails.getTransferAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transferDetails.getTransferAmount()));
        

        model.addAttribute("successMessage", "Funds transferred successfully.");
        return "success";

    }
}
