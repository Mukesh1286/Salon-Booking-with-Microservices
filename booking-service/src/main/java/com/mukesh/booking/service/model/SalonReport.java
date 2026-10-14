package com.mukesh.booking.service.model;

import lombok.Data;

@Data

public class SalonReport {

    private Long salonId;
    private String salonName;
    private int totalEarning;
    private Integer totalBookings;
    private Integer canceledBooking;
    private Double totalRefund;




}
