package mk.ukim.finki.wp.kol2024g1.repository;

import mk.ukim.finki.wp.kol2024g1.model.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaSpecificationRepository<Reservation, Long> {

}
