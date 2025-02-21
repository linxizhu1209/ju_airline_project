package org.example.paymenttest.repository;

import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, String> {

}
