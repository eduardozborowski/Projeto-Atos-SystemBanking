package br.com.atm.dto;

import br.com.atm.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long accountNumber;
    private String agencyNumber;
    private Double balance;
    private UserAtmDTO userAtmDTO;

    public static AccountDTO convert(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAgencyNumber(account.getAgencyNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setUserAtmDTO(UserAtmDTO.convert(account.getUserAtm()));
        return accountDTO;
    }
}
