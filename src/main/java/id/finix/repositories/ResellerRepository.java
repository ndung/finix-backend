package id.finix.repositories;

import java.util.List;
import id.finix.domain.Reseller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface ResellerRepository extends JpaRepository<Reseller, String> {

    @Query("select rs from Reseller rs where rs.id = :id and rs.password = :password")
    Reseller findOne(@Param("id") String id, @Param("password") String password);

    @Query("select rs from Reseller rs where rs.upline = :upline")
    List<Reseller> findDownlines(@Param("upline") String upline);
}
