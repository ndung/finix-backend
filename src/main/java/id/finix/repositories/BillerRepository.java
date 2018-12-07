package id.finix.repositories;

import id.finix.domain.Biller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillerRepository extends JpaRepository<Biller, String> {

    @Query("select b from Biller b where b.subcategory=:category and b.status = 1 order by b.description asc")
    List<Biller> getActiveBillers(@Param("category") int category);
}
