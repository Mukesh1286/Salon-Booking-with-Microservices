package com.mukesh.service.offering.controller;

import com.mukesh.service.offering.model.ServiceOffering;
import com.mukesh.service.offering.payload.dto.CategoryDTO;
import com.mukesh.service.offering.payload.dto.SalonDTO;
import com.mukesh.service.offering.payload.dto.ServiceDTO;
import com.mukesh.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping()
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
            ){

        SalonDTO salonDTO  =new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

       ServiceOffering serviceOfferings = serviceOfferingService
               .createService(salonDTO, serviceDTO, categoryDTO);
        return ResponseEntity.ok(serviceOfferings);
    }

    @PutMapping()
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering
    ) throws Exception {


        ServiceOffering serviceOfferings = serviceOfferingService
                .updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }
}
