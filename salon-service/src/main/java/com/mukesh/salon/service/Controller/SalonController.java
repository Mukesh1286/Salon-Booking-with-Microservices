package com.mukesh.salon.service.Controller;

import com.mukesh.salon.service.mapper.SalonMapper;
import com.mukesh.salon.service.model.Salon;
import com.mukesh.salon.service.payload.dto.SalonDTO;
import com.mukesh.salon.service.payload.dto.UserDTO;
import com.mukesh.salon.service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;


    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);

    }


    @PatchMapping("/{id}")
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable("id") Long salonId,
            @RequestBody SalonDTO salonDTO) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.updateSalon(salonDTO, userDTO, salonId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);

    }

    @GetMapping()
    public ResponseEntity <List<SalonDTO>> getSalons() throws Exception {

        List<Salon> salons = salonService.getAllSalons();

        List<SalonDTO> salonDTOS = salons.stream().map(salon ->
                {
                    SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
                    return salonDTO1;
                }

                ).toList();

        return ResponseEntity.ok(salonDTOS);

    }

    @GetMapping("/{salonId}")
    public ResponseEntity <SalonDTO> getSalons(@PathVariable Long salonId) throws Exception {


        Salon salons = salonService.getSalonById(salonId);

        SalonDTO salonDTOS= SalonMapper.mapToDTO(salons);

        return ResponseEntity.ok(salonDTOS);

    }

    @GetMapping("/search")
    public ResponseEntity <List<SalonDTO>> serarchSalon(
            @RequestParam("city") String city
    ) throws Exception {


        List<Salon> salons = salonService.searchSalonByCity(city);

        List<SalonDTO> salonDTOS = salons.stream().map(salon ->
                {
                    SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
                    return salonDTO1;
                }

        ).toList();

        return ResponseEntity.ok(salonDTOS);

    }
    @GetMapping("/owner")
    public ResponseEntity <SalonDTO> getSalonByOwnerId(@PathVariable Long salonId) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salons = salonService.getSalonByOwnerId(userDTO.getId());

        SalonDTO salonDTOS= SalonMapper.mapToDTO(salons);

        return ResponseEntity.ok(salonDTOS);

    }
}
