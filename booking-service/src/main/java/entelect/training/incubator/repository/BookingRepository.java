package entelect.training.incubator.repository;

import entelect.training.incubator.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    Optional<Booking> findById (Integer id);
//
//    Optional<Customer> findByPassportNumber(String passportNumber);
//
    List<Booking> findByCustomerId(Integer id);

    List<Booking> findByReferenceNumber(String referenceNumber);

    Booking save(Booking b);
//    Optional<Customer> findByUsername(String username);
}
