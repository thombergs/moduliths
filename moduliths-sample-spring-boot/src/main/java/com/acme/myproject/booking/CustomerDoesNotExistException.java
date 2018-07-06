package com.acme.myproject.booking;

class CustomerDoesNotExistException extends RuntimeException {

  CustomerDoesNotExistException(Long customerId) {
    super(String.format("A customer with ID '%d' doesn't exist!", customerId));
  }

}
