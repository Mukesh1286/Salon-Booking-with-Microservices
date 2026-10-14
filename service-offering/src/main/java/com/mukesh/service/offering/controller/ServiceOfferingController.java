package com.mukesh.service.offering.controller;

import com.mukesh.service.offering.model.ServiceOffering;
import com.mukesh.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering")
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getAllServiceBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceBySalonId(
            @PathVariable Long id
    ) throws Exception {
        ServiceOffering serviceOfferings = serviceOfferingService.getServiceById(id);

        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServiceByIds(
            @PathVariable Set<Long> ids,
            @RequestParam(required = false) Long categoryId
    ){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServiceByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }

}
