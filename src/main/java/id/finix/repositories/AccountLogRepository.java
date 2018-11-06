package id.finix.repositories;

import id.finix.domain.AccountLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {

    @Query("select a from AccountLog a where a.resellerId = :resellerId and a.date between :startDate and :endDate order by a.id desc")
    List<AccountLog> getAccountLogs(@Param("resellerId") String resellerId,
                                    @Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate,
                                    Pageable pageable);
}
