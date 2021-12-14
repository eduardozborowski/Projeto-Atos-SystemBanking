package br.com.atm.repository;

import br.com.atm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserAtmId(Long id);
}
