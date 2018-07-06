package com.acme.myproject.booking;

import com.acme.myproject.customer.Customer;
import com.acme.myproject.customer.CustomerRepository;
import com.acme.myproject.flight.Flight;
import com.acme.myproject.flight.FlightService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  private BookingRepository bookingRepository;

  private CustomerRepository customerRepository;

  FlightService flightService;

  public BookingService(
      BookingRepository bookingRepository,
      CustomerRepository customerRepository, FlightService flightService) {
    this.bookingRepository = bookingRepository;
    this.customerRepository = customerRepository;
    this.flightService = flightService;
  }

  /**
   * Books the given flight for the given customer.
   */
  public BookingEntity bookFlight(Long customerId, String flightNumber) {

    Optional<Customer> customer = customerRepository.findById(customerId);
    if (!customer.isPresent()) {
      throw new CustomerDoesNotExistException(customerId);
    }

    Optional<Flight> flight = flightService.findFlight(flightNumber);
    if (!flight.isPresent()) {
      throw new FlightDoesNotExistException(flightNumber);
    }

    BookingEntity booking = BookingEntity.builder()
        .customer(customer.get())
        .flightNumber(flight.get().getFlightNumber())
        .build();

    return this.bookingRepository.save(booking);
  }

}
