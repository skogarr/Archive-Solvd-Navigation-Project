package org.laba.service;

//import mapper.MetroRouteMapper;

import lombok.*;
import org.laba.model.MetroRoute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.MetroRouteRequest;
import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class MetroRouteService {

    private final MetroRouteMapper metroRouteMapper;

    @GetMapping("/{id}")
    public Optional<MetroRoute> findMetroRoute(@PathVariable long id) {
        return Optional.ofNullable(metroRouteMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity createMetroRoute(@Valid @RequestBody MetroRouteRequest request) {
        metroRouteMapper.createEntity(new MetroRoute(request.getId(), request.getRouteName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public MetroRoute updateMetroRoute(@PathVariable long id, @Valid @RequestBody MetroRouteRequest request) {
        metroRouteMapper.updateEntity(new MetroRoute(id, request.getRouteName()));
        return metroRouteMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMetroRoute(@PathVariable long id) {
        metroRouteMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
