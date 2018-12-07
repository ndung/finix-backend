package id.finix.repositories;

import id.finix.domain.Deposit;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query(value = "SELECT nextval('DEPOSIT')", nativeQuery = true)
    int getStanDeposit();

    @Query(value = "SELECT nextval('TIKET_DEPOSIT')", nativeQuery = true)
    int getUniqueTicketNumber();

    @Query("select d from Deposit d where d.resellerId = :resellerId and d.status = '0' and d.datetime between :startDate and :endDate order by d.datetime desc")
    List<Deposit> getUnapprovedDeposit(@Param("resellerId") String resellerId,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate,
                                Pageable pageable);

    @Query("select d from Deposit d where d.resellerId = :resellerId order by d.datetime desc")
    List<Deposit> getDeposits(@Param("resellerId") String resellerId,
                              Pageable pageable);
}
