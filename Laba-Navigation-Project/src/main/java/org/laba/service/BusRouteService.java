package org.laba.service;

//import mapper.BusRoute;

import lombok.*;
import org.laba.model.BusRoute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.BusRouteRequest;


import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class BusRouteService {

    private final BusRouteMapper busRouteMapper;

    @GetMapping("/{id}")
    public Optional<BusRoute> findBusRoute(@PathVariable long id) {
        return Optional.ofNullable(busRouteMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity createBusRoute(@Valid @RequestBody BusRouteRequest request) {
        busRouteMapper.createEntity(new BusRoute(request.getId(), request.getRouteName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public BusRoute updateBusRoute(@PathVariable long id, @Valid @RequestBody BusRouteRequest request) {
        busRouteMapper.updateEntity(new BusRoute(id, request.getRouteName()));
        return busRouteMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBusRoute(@PathVariable long id) {
        busRouteMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
