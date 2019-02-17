package id.finix.repositories;

import id.finix.domain.RedeemLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RedeemRepository extends JpaRepository<RedeemLog, Long> {

    @Query("select r from RedeemLog r where r.resellerId = :resellerId order by r.timestamp desc")
    List<RedeemLog> getLogs(@Param("resellerId") String resellerId, Pageable pageable);
}
