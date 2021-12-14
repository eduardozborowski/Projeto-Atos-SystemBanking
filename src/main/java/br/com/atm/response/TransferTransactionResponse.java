package br.com.atm.response;

import br.com.atm.dto.AccountDTO;
import br.com.atm.entity.Account;
import br.com.atm.entity.BankingTransaction;
import br.com.atm.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferTransactionResponse {
    private Double transferValue;
    private AccountDTO originAccount;
    private AccountDTO destinationAccount;
    private TransactionType transactionType;

    public TransferTransactionResponse(BankingTransaction bankingTransaction, Account destinationAccount){
        this.transferValue = Math.abs(bankingTransaction.getValueTransaction());
        this.originAccount = AccountDTO.convert(bankingTransaction.getAccount());
        this.destinationAccount = AccountDTO.convert(destinationAccount);
        this.transactionType = bankingTransaction.getTransactionType();

    }
}
