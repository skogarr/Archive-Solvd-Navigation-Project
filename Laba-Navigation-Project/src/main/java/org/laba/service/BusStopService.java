package org.laba.service;

//import mapper.BusStop;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laba.model.BusRoute;
import org.laba.model.BusStop;
import org.laba.model.TransitPoint;
import org.laba.request.BusStopRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class BusStopService {

    private final IBusStopDAO busStopDAO;
    private final IBusRouteDAO busRouteDAO;
    private final ITransitPointDAO transitPointDAO;

    @GetMapping("/{id}")
    public Optional<BusStop> find(@PathVariable long id) {
        return Optional.ofNullable(busStopDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody BusStopRequest request) {
        busStopDAO.createEntity(new BusStop(request.getId(), request.getBusRouteId(), request.getTransitPointId(), request.getStopNo()));
        BusRoute busRoute = new BusRoute();
        TransitPoint transitPoint = new TransitPoint();
        if (request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if (request.getBusRouteId() != busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            busRouteDAO.createEntity(new BusRoute(request.getBusRouteId(), null));
        } else if (request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() != transitPoint.getId()) {
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        } else {
            busRouteDAO.createEntity(new BusRoute(request.getBusRouteId(), null));
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public BusStop update(@PathVariable long id, @Valid @RequestBody BusStopRequest request) {
        busStopDAO.updateEntity(new BusStop(request.getId(), request.getBusRouteId(), request.getTransitPointId(), request.getStopNo()));
        BusRoute busRoute = new BusRoute();
        TransitPoint transitPoint = new TransitPoint();
        if (request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return busStopDAO.getEntityById(id);
        } else if (request.getBusRouteId() != busRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            busRouteDAO.createEntity(new BusRoute(request.getBusRouteId(), null));
        } else if (request.getBusRouteId() == busRoute.getId() && request.getTransitPointId() != transitPoint.getId()) {
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        } else {
            busRouteDAO.createEntity(new BusRoute(request.getBusRouteId(), null));
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return busStopDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        busStopDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
