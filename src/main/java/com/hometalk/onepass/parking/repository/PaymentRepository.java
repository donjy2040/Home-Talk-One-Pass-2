package com.hometalk.onepass.parking.repository;

import com.hometalk.onepass.parking.entity.Payment;
import com.hometalk.onepass.parking.entity.ParkingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}