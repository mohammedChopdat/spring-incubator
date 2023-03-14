package entelect.training.incubator.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="customer_id")
    private Integer customerId;

    @Column(name="flight_id")
    private Integer flightId;


    @Column(name="booking_date")
    private String bookingDate;

    @Column(name=" reference_number")
    private String referenceNumber;

}
