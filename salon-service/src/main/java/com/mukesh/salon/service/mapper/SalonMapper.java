package com.mukesh.salon.service.mapper;

import com.mukesh.salon.service.model.Salon;
import com.mukesh.salon.service.payload.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon){

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());

        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setImages(salon.getImages());
        salonDTO.setName(salon.getName());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
       salonDTO.setOpenTime(salon.getOpenTime());
       salonDTO.setCloseTime(salon.getCloseTime());

        return salonDTO;

    }

}
