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

    private final ITramRouteDAO tramRouteDAO;

    @GetMapping("/{id}")
    public Optional<TramRoute> find(@PathVariable long id) {
        return Optional.ofNullable(tramRouteDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody TramRouteRequest request) {
        tramRouteDAO.createEntity(new TramRoute(request.getId(), request.getRouteName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public TramRoute update(@PathVariable long id, @Valid @RequestBody TramRouteRequest request) {
        tramRouteDAO.updateEntity(new TramRoute(id, request.getRouteName()));
        return tramRouteDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        tramRouteDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
