package br.com.atm.request;

import br.com.atm.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private AccountRequest account;
    private AccountRequest destinationAccount;
    private TransactionType transactionType;
    private Double value;
}
