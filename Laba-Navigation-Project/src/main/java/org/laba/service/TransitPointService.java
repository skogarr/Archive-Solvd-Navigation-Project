package org.laba.service;

//import mapper.TransitPoint;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laba.model.TransitPoint;
import org.laba.request.TransitPointRequest;
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
public class TransitPointService {

    private final ITransitPointDAO transitPointDAO;

    @GetMapping("/{id}")
    public Optional<TransitPoint> find(@PathVariable long id) {
        return Optional.ofNullable(transitPointDAO.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody TransitPointRequest request) {
        transitPointDAO.createEntity(new TransitPoint(request.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public TransitPoint update(@PathVariable long id, @Valid @RequestBody TransitPointRequest request) {
        transitPointDAO.updateEntity(new TransitPoint(id));
        return transitPointDAO.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        transitPointDAO.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
