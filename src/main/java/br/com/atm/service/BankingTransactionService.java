package br.com.atm.service;

import br.com.atm.dto.AccountDTO;
import br.com.atm.entity.Account;
import br.com.atm.entity.BankingTransaction;
import br.com.atm.entity.UserAtm;
import br.com.atm.repository.AccountRepository;
import br.com.atm.repository.BankingTransactionRepository;
import br.com.atm.repository.UserAtmRepository;
import br.com.atm.request.TransactionRequest;
import br.com.atm.response.BankingTransactionResponse;
import br.com.atm.response.TransferTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankingTransactionService {

    private final BankingTransactionRepository bankingTransactionRepository;
    private final UserAtmRepository userAtmRepository;
    private final AccountRepository accountRepository;


    @Transactional
    public BankingTransactionResponse transaction(TransactionRequest transactionRequest) {

        var account = findByAccount(transactionRequest.getAccount().getAccountNumber());

        var transaction = new BankingTransaction(transactionRequest, account, account.getUserAtm());

        if (transaction.getInsufficientBalance()){
            return null;
        }

        account.setBalance(transaction.getBalanceBefore());

        bankingTransactionRepository.save(transaction);
        accountRepository.save(account);

        return new BankingTransactionResponse(transaction);

    }

    @Transactional
    public TransferTransactionResponse transfer(TransactionRequest transactionRequest) {

        var originAccount = findByAccount(transactionRequest.getAccount().getAccountNumber());
        var destinationAccount = findByAccount(transactionRequest.getDestinationAccount().getAccountNumber());

        var transaction = new BankingTransaction(transactionRequest, originAccount, originAccount.getUserAtm());

        if (transaction.getInsufficientBalance()){
            return null;
        }

        originAccount.setBalance(transaction.getBalanceBefore());

        destinationAccount.setBalance(destinationAccount.getBalance() + transactionRequest.getValue());

        bankingTransactionRepository.save(transaction);
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        return new TransferTransactionResponse(transaction, destinationAccount);

    }

    public AccountDTO findWithdraw(Long accountNumber){
        var account = this.findByAccount(accountNumber);
        return AccountDTO.convert(account);
    }

    @Transactional(readOnly = true)
    private Account findByAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber).get();
    }

    @Transactional(readOnly = true)
    private UserAtm findByUser(Long userId) {
        return userAtmRepository.findById(userId).get();
    }

}
