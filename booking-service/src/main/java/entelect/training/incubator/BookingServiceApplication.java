package entelect.training.incubator;


import entelect.training.incubator.spring.customer.model.Customer;
import entelect.training.incubator.spring.customer.service.CustomersService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BookingServiceApplication {

@Autowired
    static CustomersService customersService;

    public static void main(String[] args) {

        SpringApplication.run(BookingServiceApplication.class, args);

    }

}
