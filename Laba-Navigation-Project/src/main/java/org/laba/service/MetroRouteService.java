package org.laba.service;

//import mapper.MetroRouteMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laba.model.MetroRoute;
import org.laba.request.MetroRouteRequest;
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
public class MetroRouteService {

    private final IMetroRouteDAO metroRouteDAO;

    @GetMapping("/{id}")
    public Optional<MetroRoute> find(@PathVariable long id) {
        return Optional.ofNullable(metroRouteDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody MetroRouteRequest request) {
        metroRouteDAO.createEntity(new MetroRoute(request.getId(), request.getRouteName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public MetroRoute update(@PathVariable long id, @Valid @RequestBody MetroRouteRequest request) {
        metroRouteDAO.updateEntity(new MetroRoute(id, request.getRouteName()));
        return metroRouteDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        metroRouteDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
