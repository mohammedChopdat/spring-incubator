package entelect.training.incubator.controller;

import com.baeldung.springsoap.client.gen.CaptureRewardsRequest;
import com.baeldung.springsoap.client.gen.CaptureRewardsResponse;
import com.baeldung.springsoap.client.gen.RewardsBalanceResponse;
import entelect.training.incubator.model.Booking;
import entelect.training.incubator.model.SearchBooking;
import entelect.training.incubator.service.BookingsService;

import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.service.CustomersService;
import entelect.training.incubator.spring.flight.model.Flight;
import entelect.training.incubator.spring.loyalty.client.LoyaltyClient;
import entelect.training.incubator.spring.loyalty.server.RewardsServiceImpl;
import entelect.training.incubator.spring.notification.sms.client.impl.MoloCellSmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingsService bookingsService;
    private final CustomersService customersService;

    private final RewardsServiceImpl rewardsService;

    private final MoloCellSmsClient moloCellSmsClient;

    @Autowired
    private final LoyaltyClient client;



    @Autowired
    public BookingController(BookingsService bookingsService, CustomersService customersService, RewardsServiceImpl rewardsService, MoloCellSmsClient moloCellSmsClient, LoyaltyClient client) {
        this.bookingsService = bookingsService;

        this.customersService = customersService;
        this.rewardsService = rewardsService;
        this.moloCellSmsClient = moloCellSmsClient;
        this.client = client;
    }


   @GetMapping("rewards/{pass}")
   public ResponseEntity<?> getRewardsByPass(@PathVariable String pass) {
       RewardsBalanceResponse response = client.rewardsBalance(pass);

       if (response != null) {
           LOGGER.trace("Found booking");
           return new ResponseEntity<>(response, HttpStatus.OK);
       }

       LOGGER.trace("Booking not found");
       return ResponseEntity.notFound().build();
   }

    @GetMapping("re/{pass}")
    public ResponseEntity<?> captureRewards(@PathVariable String pass) {
        CaptureRewardsResponse response = client.captureRewardsRequest("123", BigDecimal.valueOf(100));

        if (response != null) {
            LOGGER.trace("Found booking");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        LOGGER.info("Processing booking creation request for booking={}", booking);
        Customer c = new Customer();
        c.setFirstName("jan");
        c.setLastName("las");
        c.setPassportNumber("12344");
        c.setEmail("e@a.com");
        c.setUsername("eee");
        c.setPhoneNumber("123345");
        Customer returned = customersService.createCustomer(c);
        System.out.println(returned);
        System.out.println("hello");


       Object customer = getRequest("http://localhost:8201/customers/",booking.getCustomerId(),Customer.class);
       Object flight = getRequest("http://localhost:8201/flights/",booking.getFlightId(),Flight.class);
       if(customer!=null && flight !=null){
           System.out.println(customer);

               final Booking savedFlight = bookingsService.createBooking(booking);
               Customer customer1 = (Customer) customer;
               Flight flight1 = (Flight)flight;
               moloCellSmsClient.sendSms(customer1.getPhoneNumber(),"Hello your booking is confirmed");
               BigDecimal cost = BigDecimal.valueOf(flight1.getSeatCost());
               BigDecimal rewards = cost.add(client.rewardsBalance(customer1.getPassportNumber()).getBalance());
               client.captureRewardsRequest(customer1.getPassportNumber(),rewards);

               LOGGER.trace("Booking created");
               return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);

       }




        return ResponseEntity.notFound().build();
    }

    private Object getRequest(String url,Integer parameter,Class t){
        String uri = url+parameter;
        RestTemplate restTemplate = new RestTemplate();
        Object o = restTemplate.getForEntity(uri,t).getBody();
        return o;
    }

    @PostMapping("search")
    public ResponseEntity<?> getBookingsByCustomerId(@RequestBody SearchBooking customerToSearch) {
       // LOGGER.info("Processing booking search request for booking customer_id={}", id);
        List<Booking> booking = this.bookingsService.searchCustomers(customerToSearch);

        if (!booking.isEmpty()) {
            LOGGER.trace("Found bookings");
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }

        LOGGER.trace("Bookings not found");
        return ResponseEntity.notFound().build();
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getBookingsById(@PathVariable Integer id) {
        LOGGER.info("Processing booking search request for booking id={}", id);
        Booking booking = this.bookingsService.getBooking(id);

        if (booking != null) {
            LOGGER.trace("Found booking");
            return new ResponseEntity<>("Hello", HttpStatus.OK);
        }

        LOGGER.trace("Booking not found");
        return ResponseEntity.notFound().build();
    }
}
