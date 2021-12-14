package br.com.atm.response;

import br.com.atm.dto.AccountDTO;
import br.com.atm.entity.BankingTransaction;
import br.com.atm.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankingTransactionResponse {
    private Long transactionId;
    private AccountDTO account;
    private Double balanceAfter;
    private Double balanceBefore;
    private Double valueTransaction;
    private TransactionType transactionType;
    private LocalDate transactionDate;

    public BankingTransactionResponse (BankingTransaction bankingTransaction){
        this.transactionId = bankingTransaction.getId();
        this.account = AccountDTO.convert(bankingTransaction.getAccount());
        this.valueTransaction = bankingTransaction.getValueTransaction();
        this.balanceAfter = bankingTransaction.getBalanceAfter();
        this.balanceBefore = bankingTransaction.getBalanceBefore();
        this.transactionType = bankingTransaction.getTransactionType();
    }
}
