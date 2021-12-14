package br.com.atm.entity;

import br.com.atm.entity.enums.TransactionType;
import br.com.atm.request.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankingTransaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserAtm userAtm;

    @OneToOne
    private Account account;

    private Double balanceBefore;
    private Double balanceAfter;
    private Double valueTransaction;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Boolean insufficientBalance = Boolean.FALSE;

    public BankingTransaction(TransactionRequest transactionRequest, Account account, UserAtm user){
        this.userAtm = user;
        this.account = account;
        this.transactionType = transactionRequest.getTransactionType();
        this.balanceAfter = account.getBalance();

        boolean response = this.setTransactionValues(transactionRequest);
        if (response){
            this.insufficientBalance = true;
        }
    }


    private boolean setTransactionValues(TransactionRequest transactionRequest) {
        if("WITHDRAW".equalsIgnoreCase(transactionRequest.getTransactionType().name()) || "TRANSFER".equalsIgnoreCase(transactionRequest.getTransactionType().name())) {
            boolean insufficientBalance = this.validateBalance(account.getBalance(), transactionRequest.getValue());
            if(insufficientBalance){
                return true;
            }
            this.valueTransaction = transactionRequest.getValue() * (-1);
        }else{
            this.valueTransaction = transactionRequest.getValue();
        }
        this.balanceBefore = this.valueTransaction + this.balanceAfter;

        return false;
    }

    private boolean validateBalance(Double balance, Double requestValue) {
        return requestValue > balance;
    }

}
