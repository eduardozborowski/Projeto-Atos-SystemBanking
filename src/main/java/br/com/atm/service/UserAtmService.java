package br.com.atm.service;

import br.com.atm.dto.AccountDTO;
import br.com.atm.repository.AccountRepository;
import br.com.atm.repository.UserAtmRepository;
import br.com.atm.request.UserAtmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAtmService {

    private final UserAtmRepository userAtmRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountDTO validatePassword(UserAtmRequest userAtmRequest){
        var userDB = userAtmRepository.findByLogin(userAtmRequest.getLogin());

        boolean valid = userDB.filter(userAuthentication -> passwordEncoder.matches(userAtmRequest.getPassword(), userDB.get().getPassword())).isPresent();

        if(valid){
            var account = accountRepository.findById(userDB.get().getId()).get();
            return AccountDTO.convert(account);
        }

        return null;
    }

}
