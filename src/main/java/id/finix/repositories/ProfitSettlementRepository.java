package id.finix.repositories;

import id.finix.domain.ProfitSettlement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProfitSettlementRepository extends JpaRepository<ProfitSettlement, Long> {

    @Query("select s from ProfitSettlement s where s.resellerId = :resellerId order by s.timestamp desc")
    List<ProfitSettlement> getLogs(@Param("resellerId") String resellerId, Pageable pageable);
}
