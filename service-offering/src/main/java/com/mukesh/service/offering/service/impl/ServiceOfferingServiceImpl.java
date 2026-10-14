package com.mukesh.service.offering.service.impl;

import com.mukesh.service.offering.model.ServiceOffering;
import com.mukesh.service.offering.payload.dto.CategoryDTO;
import com.mukesh.service.offering.payload.dto.SalonDTO;
import com.mukesh.service.offering.payload.dto.ServiceDTO;
import com.mukesh.service.offering.repository.ServiceOfferingRepository;
import com.mukesh.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;


    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {

       ServiceOffering serviceOffering = new ServiceOffering();
       serviceOffering.setImage(serviceDTO.getImage());
       serviceOffering.setSalonId(salonDTO.getId());
       serviceOffering.setName(serviceDTO.getName());
       serviceOffering.setDescription(serviceDTO.getDescription());
       serviceOffering.setCategoryId(categoryDTO.getId());
       serviceOffering.setPrice(serviceDTO.getPrice());
       serviceOffering.setDuration(serviceDTO.getDuration());

       return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering serviceDTO) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);

        if(serviceOffering == null){
            throw new Exception("service not exist with idS" +serviceId);
        }

        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());

        return serviceOfferingRepository.save(serviceOffering);

    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {

       Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);

       //category is provided return all services
       if(categoryId!= null){
           services = services.stream().filter((service) ->service.getCategoryId()
                   !=null && service.getCategoryId()==categoryId).collect(Collectors.toSet());
       }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);

        if(serviceOffering == null){
            throw new Exception("service not exist with idS" +id);
        }
        return serviceOffering;
    }



}
