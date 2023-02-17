package org.laba.service;

//import mapper.MetroStops;
import lombok.*;
import org.laba.model.MetroStops;
import org.laba.model.MetroRoute;
import org.laba.model.TransitPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.MetroStopsRequest;
import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class MetroStopsService {

    private final MetroStopsMapper metroStopsMapper;
    private final MetroRouteMapper metroRouteMapper;
    private final TransitPointMapper transitPointMapper;

    @GetMapping("/{id}")
    public Optional<MetroStops> find(@PathVariable long id) {
        return Optional.ofNullable(metroStopsMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody  MetroStopsRequest request) {
        metroStopsMapper.createEntity(new MetroStops(request.getId(), request.getMetroRouteId(), request.getTransitPointId(), request.getStopNo()));
        MetroRoute metroRoute = new MetroRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else if(request.getMetroRouteId() != metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            metroRouteMapper.createEntity(new MetroRoute(request.getMetroRouteId(), null));
        }
        else if(request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            metroRouteMapper.createEntity(new MetroRoute(request.getMetroRouteId(), null));
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public MetroStops update(@PathVariable long id, @Valid @RequestBody MetroStopsRequest request) {
        metroStopsMapper.updateEntity(new MetroStops(request.getId(), request.getMetroRouteId(), request.getTransitPointId(), request.getStopNo()));
        MetroRoute metroRoute = new MetroRoute();
        TransitPoint transitPoint = new TransitPoint();
        if(request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()) {
            return metroStopsMapper.getEntityById(id);
        }
        else if(request.getMetroRouteId() != metroRoute.getId() && request.getTransitPointId() == transitPoint.getId()){
            metroRouteMapper.createEntity(new MetroRoute(request.getMetroRouteId(), null));
        }
        else if(request.getMetroRouteId() == metroRoute.getId() && request.getTransitPointId() != transitPoint.getId()){
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        else{
            metroRouteMapper.createEntity(new MetroRoute(request.getMetroRouteId(), null));
            transitPointMapper.createEntity(new TransitPoint(request.getTransitPointId()));
        }
        return metroStopsMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        metroStopsMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
