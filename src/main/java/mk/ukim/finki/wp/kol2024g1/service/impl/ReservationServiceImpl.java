package mk.ukim.finki.wp.kol2024g1.service.impl;

import mk.ukim.finki.wp.kol2024g1.model.Hotel;
import mk.ukim.finki.wp.kol2024g1.model.Reservation;
import mk.ukim.finki.wp.kol2024g1.model.RoomType;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidHotelIdException;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidReservationIdException;
import mk.ukim.finki.wp.kol2024g1.repository.HotelRepository;
import mk.ukim.finki.wp.kol2024g1.repository.ReservationRepository;
import mk.ukim.finki.wp.kol2024g1.service.ReservationService;
import mk.ukim.finki.wp.kol2024g1.service.filter_types.FieldFilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }


    @Override
    public List<Reservation> listAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(InvalidReservationIdException::new);
    }

    @Override
    public Reservation create(String guestName, LocalDate dateCreated, Integer daysOfStay, RoomType roomType, Long hotelId) {
        Hotel h = hotelRepository.findById(hotelId).orElseThrow(InvalidHotelIdException::new);
        return reservationRepository.save(new Reservation(guestName, dateCreated, daysOfStay, roomType, h));
    }

    @Override
    public Reservation update(Long id, String guestName, LocalDate dateCreated, Integer daysOfStay, RoomType roomType, Long hotelId) {
        Hotel h = hotelRepository.findById(hotelId).orElseThrow(InvalidHotelIdException::new);
        Reservation r = reservationRepository.findById(id).orElseThrow(InvalidReservationIdException::new);

        r.setGuestName(guestName);
        r.setDateCreated(dateCreated);
        r.setDaysOfStay(daysOfStay);
        r.setRoomType(roomType);
        r.setHotel(h);

        return reservationRepository.save(r);
    }

    @Override
    public Reservation delete(Long id) {
        Reservation r = reservationRepository.findById(id).orElseThrow(InvalidReservationIdException::new);
        reservationRepository.delete(r);
        return r;
    }

    @Override
    public Reservation extendStay(Long id) {
        Reservation r = reservationRepository.findById(id).orElseThrow(InvalidReservationIdException::new);
        r.setDaysOfStay(r.getDaysOfStay() + 1);
        reservationRepository.save(r);
        return r;
    }

    @Override
    public Page<Reservation> findPage(String guestName, RoomType roomType, Long hotel, int pageNum, int pageSize) {

        Specification<Reservation> filter = Specification.where(FieldFilterSpecification
                        .filterContainsText(Reservation.class, "guestName", guestName))
                .and(FieldFilterSpecification.filterEqualsV(Reservation.class, "hotel.id", hotel))
                .and(FieldFilterSpecification.filterEqualsV(Reservation.class, "roomType", roomType));

        return reservationRepository.findAll(filter, PageRequest.of(pageNum - 1, pageSize));
    }
}
