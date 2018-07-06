package com.acme.myproject.booking;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.acme.myproject.booking.BookingRepository;
import com.acme.myproject.customer.CustomerRepository;
import de.olivergierke.moduliths.model.test.ModuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ModuleTest
public class BookingRepositoryTest {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired(required = false)
  private CustomerRepository customerRepository;

  @Test
  public void test(){
    assertThat(bookingRepository.count()).isEqualTo(0);
  }

  @Test
  public void repositoryFromOtherModuleIsNotLoaded(){
    assertThat(customerRepository).isNull();
  }

}