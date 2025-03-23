package org.example.paymenttest.repository;

import org.example.paymenttest.entity.FlightOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightOrderRepository extends JpaRepository<FlightOrder, Long> {

    @Query("SELECT fo FROM FlightOrder fo JOIN FETCH fo.flightSchedule WHERE fo.user.id = :userId")
    List<FlightOrder> findFlightOrderListByUserId(@Param("userId") Long userId);
}
