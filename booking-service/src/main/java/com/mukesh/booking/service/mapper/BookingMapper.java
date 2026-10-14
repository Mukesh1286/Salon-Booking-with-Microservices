package com.mukesh.booking.service.mapper;

import com.mukesh.booking.service.model.Booking;
import com.mukesh.booking.service.payload.dto.BookingDTO;
import lombok.Data;

@Data
public class BookingMapper {

    public static BookingDTO toDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSalonId(booking.getSalonId());
        booking.setServiceIds(booking.getServiceIds());

        return bookingDTO;

    }

}
