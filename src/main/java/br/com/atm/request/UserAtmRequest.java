package br.com.atm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAtmRequest {
    private Long id;
    private String login;
    private String password;
    private AccountRequest accountRequest;
}
