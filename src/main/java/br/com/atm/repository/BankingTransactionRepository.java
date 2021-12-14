package br.com.atm.repository;

import br.com.atm.entity.BankingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingTransactionRepository extends JpaRepository<BankingTransaction, Long> {
}
