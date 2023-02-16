package org.laba.service;

//import mapper.BusStop;

import lombok.*;
import org.laba.model.BusRoute;
import org.laba.model.BusStop;
import org.laba.model.TransitPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.BusStopRequest;

import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class BusStopService {

    private final BusStopMapper busStopMapper;

    @GetMapping("/{id}")
    public Optional<BusStop> findBusStop(@PathVariable long busRouteId) {
        return Optional.ofNullable(busStopMapper.getEntityById(busRouteId));
    }

    @PostMapping("/")
    public ResponseEntity createBusStop(@Valid @RequestBody BusStopRequest request) {
        busStopMapper.createEntity(new BusStop(request.getBusRouteId(), request.getTransitPointId(), request.getStopNo()));
        BusRoute busRoute = new BusRoute();
        if(request.getBusRouteId() == busRoute.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else{
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public BusStop updateBusStop(@PathVariable long busRouteId, @Valid @RequestBody BusStopRequest request) {
        busStopMapper.updateEntity(new BusStop(request.getBusRouteId(), request.getTransitPointId(), request.getStopNo()));
        BusRoute busRoute = new BusRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return busStopMapper.getEntityById(busRouteId);
        }
        else if(request.getBusRouteId() != busRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
        }
        else if(request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            busTransitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
            busTransitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return busStopMapper.getEntityById(busRouteId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBusStop(@PathVariable long busRouteId) {
        busStopMapper.removeEntity(busRouteId);
        return ResponseEntity.noContent().build();
    }
}
