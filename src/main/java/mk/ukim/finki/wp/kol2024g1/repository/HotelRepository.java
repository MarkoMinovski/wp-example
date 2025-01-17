package mk.ukim.finki.wp.kol2024g1.repository;

import mk.ukim.finki.wp.kol2024g1.model.Hotel;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaSpecificationRepository<Hotel, Long> {
}
