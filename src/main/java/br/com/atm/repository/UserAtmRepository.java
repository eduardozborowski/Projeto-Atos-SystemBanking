package br.com.atm.repository;

import br.com.atm.entity.UserAtm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAtmRepository extends JpaRepository<UserAtm, Long> {
    Optional<UserAtm> findByLogin(String login);
}
