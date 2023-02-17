package org.laba.service;

//import mapper.BusRoute;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laba.model.BusRoute;
import org.laba.request.BusRouteRequest;
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
public class BusRouteService {

    private final IBusRouteDAO busRouteDAO;

    @GetMapping("/{id}")
    public Optional<BusRoute> find(@PathVariable long id) {
        return Optional.ofNullable(busRouteDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody BusRouteRequest request) {
        busRouteDAO.createEntity(new BusRoute(request.getId(), request.getRouteName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public BusRoute update(@PathVariable long id, @Valid @RequestBody BusRouteRequest request) {
        busRouteDAO.updateEntity(new BusRoute(id, request.getRouteName()));
        return busRouteDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        busRouteDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
