package br.com.atm.controller;

import br.com.atm.entity.enums.TransactionType;
import br.com.atm.request.AccountRequest;
import br.com.atm.request.TransactionRequest;
import br.com.atm.request.UserAtmRequest;
import br.com.atm.response.BankingTransactionResponse;
import br.com.atm.service.BankingTransactionService;
import br.com.atm.service.UserAtmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AtmController {

    private final BankingTransactionService bankingTransactionService;
    private final UserAtmService userAtmService;


    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("user", new UserAtmRequest());
        return "welcome"; //view
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserAtmRequest userAtmRequest, Model model, HttpSession session) {
        var accountDTO = userAtmService.validatePassword(userAtmRequest);
        if (Objects.isNull(accountDTO)) {
            model.addAttribute("error", "Número de conta não existe.");
            System.out.println("Erro ao realizar o login");
            return "welcome";
        } else {
            System.out.println("Login realizado com sucesso.");
            session.setAttribute("accountNum", accountDTO.getAccountNumber());
            return "menu";
        }
    }

    @GetMapping("/menu")
    public String getMenu() {
        return "menu";
    }

    @PostMapping("/transaction")
    public String transaction(@ModelAttribute TransactionRequest transactionRequest, HttpSession session, Model model) {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber((long)session.getAttribute("accountNum"));
        transactionRequest.setAccount(accountRequest);
        transactionRequest.setTransactionType(TransactionType.valueOf((String)session.getAttribute("transactionType")));

        var transactionResponse = bankingTransactionService.transaction(transactionRequest);

        return setModelAttribute(transactionRequest, model, transactionResponse);
    }

    private String setModelAttribute(TransactionRequest transactionRequest, Model model, BankingTransactionResponse transactionResponse) {
        var returnUrl = "";

        if(Objects.nonNull(transactionResponse)){
            if(TransactionType.WITHDRAW.equals(transactionResponse.getTransactionType())){
                model.addAttribute("message", "Você sacou R$" + transactionRequest.getValue());
                returnUrl = "withdraw";
            }else{
                model.addAttribute("message", "Você depositou R$" + transactionRequest.getValue());
                returnUrl = "deposit";
            }
            model.addAttribute("balance", transactionResponse.getBalanceAfter());
            model.addAttribute("accountNum", transactionResponse.getAccount().getAccountNumber());
        }else{
            returnUrl = "insufficientFunds";
        }
        return returnUrl;
    }

    @GetMapping("/withdraw")
    public String menuWithdraw(Model model, HttpSession session) {
        session.setAttribute("transactionType", TransactionType.WITHDRAW.name());
        model.addAttribute("transactionRequest", new TransactionRequest());
        var accnum = (long)session.getAttribute("accountNum");
        model.addAttribute("accnum", accnum);
        return "withdraw"; //view
    }

    @GetMapping("/deposit")
    public String menuDeposit(Model model, HttpSession session) {
        session.setAttribute("transactionType", TransactionType.DEPOSIT.name());
        model.addAttribute("transactionRequest", new TransactionRequest());
        var accnum = (long)session.getAttribute("accountNum");
        model.addAttribute("accnum", accnum);
        return "deposit"; //view
    }


    @GetMapping("/balance")
    public String mainBalance(Model model,  HttpSession session) {
        var bal = bankingTransactionService.findWithdraw((long)session.getAttribute("accountNum")).getBalance();
        model.addAttribute("balance", bal);
        return "balance"; //view
    }

    @GetMapping("/transfer")
    public String goToTransfer(Model model, HttpSession session) {
        session.setAttribute("transactionType", TransactionType.TRANSFER.name());
        model.addAttribute("transactionRequest", new TransactionRequest());
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@ModelAttribute TransactionRequest transactionRequest, HttpSession session, Model model) {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber((long)session.getAttribute("accountNum"));
        transactionRequest.setAccount(accountRequest);
        transactionRequest.setTransactionType(TransactionType.valueOf((String)session.getAttribute("transactionType")));

        var transferResponse = bankingTransactionService.transfer(transactionRequest);

        if (Objects.nonNull(transferResponse)) {
            model.addAttribute("message", "Transferência realizada com sucesso.");
           // model.addAttribute("transaction", transferResponse.getTransactionId());
            model.addAttribute("destinationAccountNum", transferResponse.getDestinationAccount().getAccountNumber());
            model.addAttribute("originAccountNum", transferResponse.getOriginAccount().getAccountNumber());
            model.addAttribute("transferred", transferResponse.getTransferValue());
            model.addAttribute("balance", transferResponse.getOriginAccount().getBalance());
        } else {
            model.addAttribute("message", "Sem saldo para transferência. ");
        }

        return "transfer";
    }

    @GetMapping("/logout")
    public String logout(Model model,HttpSession session) {
        session.invalidate();
        model.asMap().clear();
        model.addAttribute("user", new UserAtmRequest());
        return "welcome";
    }

}


