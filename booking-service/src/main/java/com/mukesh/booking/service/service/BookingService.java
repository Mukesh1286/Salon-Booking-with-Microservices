package com.mukesh.booking.service.service;

import com.mukesh.booking.service.domain.BookingStatus;
import com.mukesh.booking.service.model.Booking;
import com.mukesh.booking.service.model.SalonReport;
import com.mukesh.booking.service.payload.dto.BookingRequest;
import com.mukesh.booking.service.payload.dto.SalonDTO;
import com.mukesh.booking.service.payload.dto.ServiceDTO;
import com.mukesh.booking.service.payload.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO user,
                          SalonDTO salon,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;
    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBysalon(Long salon);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long BookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getsalonReport(Long salonId);


}
