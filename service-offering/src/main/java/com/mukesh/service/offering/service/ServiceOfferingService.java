package com.mukesh.service.offering.service;

import com.mukesh.service.offering.model.ServiceOffering;
import com.mukesh.service.offering.payload.dto.CategoryDTO;
import com.mukesh.service.offering.payload.dto.SalonDTO;
import com.mukesh.service.offering.payload.dto.ServiceDTO;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO,
                                  ServiceDTO serviceDTO,
                                  CategoryDTO categoryDTO
                                         );

    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
