package org.laba.service;

//import mapper.TramRoute;
import lombok.*;
import org.laba.model.TramRoute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.TramRouteRequest;
import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class TramRouteService {

    private final TramRouteMapper tramRouteMapper;

    @GetMapping("/{id}")
    public Optional<TramRoute> findTramRoute(@PathVariable long id) {
        return Optional.ofNullable(tramRouteMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity createTramRoute(@Valid @RequestBody TramRouteRequest request) {
        tramRouteMapper.createEntity(new TramRoute(request.getId(), request.getRouteName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public TramRoute updateTramRoute(@PathVariable long id, @Valid @RequestBody TramRouteRequest request) {
        tramRouteMapper.updateEntity(new TramRoute(id, request.getRouteName()));
        return tramRouteMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTramRoute(@PathVariable long id) {
        tramRouteMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
