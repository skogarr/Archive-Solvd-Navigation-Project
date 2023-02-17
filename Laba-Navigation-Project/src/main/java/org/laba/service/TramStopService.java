package org.laba.service;

//import mapper.TramStop;
import lombok.*;
import org.laba.model.TramRoute;
import org.laba.model.TramStop;
import org.laba.model.TransitPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.TramStopRequest;
import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class TramStopService {

    private final TramStopMapper tramStopMapper;
    private final TramRouteMapper tramRouteMapper;
    private final TransitPointMapper transitPointMapper;

    @GetMapping("/{id}")
    public Optional<TramStop> find(@PathVariable long id) {
        return Optional.ofNullable(tramStopMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody TramStopRequest request) {
        tramStopMapper.createEntity(new TramStop(request.getId(), request.getTramRouteId(), request.getTransitPointId(), request.getStopNo()));
        TramRoute tramRoute = new TramRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else if(request.getTramRouteId() != tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            tramRouteMapper.createEntity(new TramRoute(request.getTramRouteId(), null));
        }
        else if(request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            tramRouteMapper.createEntity(new TramRoute(request.getTramRouteId(), null));
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public TramStop update(@PathVariable long id, @Valid @RequestBody TramStopRequest request) {
        tramStopMapper.updateEntity(new TramStop(request.getId(), request.getTramRouteId(), request.getTransitPointId(), request.getStopNo()));
        TramRoute tramRoute = new TramRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return tramStopMapper.getEntityById(id);
        }
        else if(request.getTramRouteId() != tramRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            tramRouteMapper.createEntity(new TramRoute(request.getTramRouteId(), null));
        }
        else if(request.getTramRouteId() == tramRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            tramRouteMapper.createEntity(new TramRoute(request.getTramRouteId(), null));
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return tramStopMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        tramStopMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
