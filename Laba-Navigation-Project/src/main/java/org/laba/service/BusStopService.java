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
    private final BusRouteMapper busRouteMapper;
    private final TransitPointMapper transitPointMapper;

    @GetMapping("/{id}")
    public Optional<BusStop> find(@PathVariable long id) {
        return Optional.ofNullable(busStopMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody BusStopRequest request) {
        busStopMapper.createEntity(new BusStop(request.getId(), request.getBusRouteId(), request.getTransitPointId(), request.getStopNo()));
        BusRoute busRoute = new BusRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else if(request.getBusRouteId() != busRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
        }
        else if(request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public BusStop update(@PathVariable long id, @Valid @RequestBody BusStopRequest request) {
        busStopMapper.updateEntity(new BusStop(request.getId(), request.getBusRouteId(), request.getTransitPointId(), request.getStopNo()));
        BusRoute busRoute = new BusRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return busStopMapper.getEntityById(id);
        }
        else if(request.getBusRouteId() != busRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
        }
        else if(request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            busRouteMapper.createEntity(new BusRoute(request.getBusRouteId(), null));
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return busStopMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        busStopMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
