package id.finix.repositories;

import id.finix.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    @Query("select b from BankAccount b where b.active = '1'")
    List<BankAccount> getBankAccounts();
}
