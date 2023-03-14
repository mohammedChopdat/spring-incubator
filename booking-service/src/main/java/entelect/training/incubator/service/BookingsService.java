package entelect.training.incubator.service;


import entelect.training.incubator.model.Booking;
import entelect.training.incubator.model.SearchBooking;
import entelect.training.incubator.model.SearchType;
import entelect.training.incubator.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class BookingsService {

    private final BookingRepository bookingRepository;

    public BookingsService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking getBooking(Integer id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }
    public List<Booking> getBookingByCustomerId(Integer id) {
        List<Booking> bookingOptional = bookingRepository.findByCustomerId(id);
        return bookingOptional;
    }


    public List<Booking> searchCustomers(SearchBooking searchRequest) {
        Map<SearchType, Supplier<List<Booking>>> searchStrategies = new HashMap<>();

        //searchStrategies.put(SearchType.NAME_SEARCH, () -> customerRepository.findByFirstNameAndLastName(searchRequest.getFirstName(), searchRequest.getLastName()));
        searchStrategies.put(SearchType.CUSTOMER_SEARCH, () -> bookingRepository.findByCustomerId(searchRequest.getCustomerId()));
        searchStrategies.put(SearchType.REFERENCE_NUMBER_SEARCH, () -> bookingRepository.findByReferenceNumber(searchRequest.getReferenceNumber()));

        List<Booking> customerOptional = searchStrategies.get(searchRequest.getSearchType()).get();

        return customerOptional;
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

}
