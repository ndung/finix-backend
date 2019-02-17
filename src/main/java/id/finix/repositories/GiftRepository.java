package id.finix.repositories;

import id.finix.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface GiftRepository extends JpaRepository<Gift, Long> {

}
