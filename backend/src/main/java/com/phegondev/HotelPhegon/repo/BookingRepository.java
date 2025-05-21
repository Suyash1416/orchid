package com.phegondev.HotelPhegon.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phegondev.HotelPhegon.entity.Booking;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
