package org.laba.service;

//import mapper.MetroStops;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laba.model.MetroRoute;
import org.laba.model.MetroStop;
import org.laba.model.TransitPoint;
import org.laba.request.MetroStopRequest;
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
public class MetroStopService {

    private final IMetroStopDAO metroStopDAO;
    private final IMetroRouteDAO metroRouteDAO;
    private final ITransitPointDAO transitPointDAO;

    @GetMapping("/{id}")
    public Optional<MetroStop> find(@PathVariable long id) {
        return Optional.ofNullable(metroStopDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody MetroStopRequest request) {
        metroStopDAO.createEntity(new MetroStop(request.getId(), request.getMetroRouteId(), request.getTransitPointId(), request.getStopNo()));
        MetroRoute metroRoute = new MetroRoute();
        TransitPoint transitPoint = new TransitPoint();
        if (request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if (request.getMetroRouteId() != metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            metroRouteDAO.createEntity(new MetroRoute(request.getMetroRouteId(), null));
        } else if (request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() != transitPoint.getId()) {
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        } else {
            metroRouteDAO.createEntity(new MetroRoute(request.getMetroRouteId(), null));
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public MetroStop update(@PathVariable long id, @Valid @RequestBody MetroStopRequest request) {
        metroStopDAO.updateEntity(new MetroStop(request.getId(), request.getMetroRouteId(), request.getTransitPointId(), request.getStopNo()));
        MetroRoute metroRoute = new MetroRoute();
        TransitPoint transitPoint = new TransitPoint();
        if (request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return metroStopDAO.getEntityById(id);
        } else if (request.getMetroRouteId() != metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            metroRouteDAO.createEntity(new MetroRoute(request.getMetroRouteId(), null));
        } else if (request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() != transitPoint.getId()) {
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        } else {
            metroRouteDAO.createEntity(new MetroRoute(request.getMetroRouteId(), null));
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return metroStopDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        metroStopDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
