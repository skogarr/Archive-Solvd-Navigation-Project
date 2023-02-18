package org.laba.service;

//import mapper.TramStop;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laba.model.TramRoute;
import org.laba.model.TramStop;
import org.laba.model.TransitPoint;
import org.laba.request.TramStopRequest;
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
public class TramStopService {

    private final ITramStopDAO tramStopDAO;
    private final ITramRouteDAO tramRouteDAO;
    private final ITransitPointDAO transitPointDAO;

    @GetMapping("/{id}")
    public Optional<TramStop> find(@PathVariable long id) {
        return Optional.ofNullable(tramStopDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody TramStopRequest request) {
        tramStopDAO.createEntity(new TramStop(request.getId(), request.getTramRouteId(), request.getTransitPointId(), request.getStopNo()));
        TramRoute tramRoute = new TramRoute();
        TransitPoint transitPoint = new TransitPoint();
        if (request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if (request.getTramRouteId() != tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            tramRouteDAO.createEntity(new TramRoute(request.getTramRouteId(), null));
        } else if (request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() != transitPoint.getId()) {
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        } else {
            tramRouteDAO.createEntity(new TramRoute(request.getTramRouteId(), null));
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public TramStop update(@PathVariable long id, @Valid @RequestBody TramStopRequest request) {
        tramStopDAO.updateEntity(new TramStop(request.getId(), request.getTramRouteId(), request.getTransitPointId(), request.getStopNo()));
        TramRoute tramRoute = new TramRoute();
        TransitPoint transitPoint = new TransitPoint();
        if (request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return tramStopDAO.getEntityById(id);
        } else if (request.getTramRouteId() != tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            tramRouteDAO.createEntity(new TramRoute(request.getTramRouteId(), null));
        } else if (request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() != transitPoint.getId()) {
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        } else {
            tramRouteDAO.createEntity(new TramRoute(request.getTramRouteId(), null));
            transitPointDAO.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return tramStopDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        tramStopDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
