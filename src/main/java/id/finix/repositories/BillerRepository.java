package id.finix.repositories;

import id.finix.domain.Biller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillerRepository extends JpaRepository<Biller, String> {

    @Query("select b from Biller b where b.subcategory in (1,2,3) and b.status = 1 order by b.description desc")
    List<Biller> getActiveEBillers();
}
