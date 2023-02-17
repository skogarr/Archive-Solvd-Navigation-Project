package org.laba.service;

//import mapper.TransitPoint;
import lombok.*;
import org.laba.model.TransitPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.laba.request.TransitPointRequest;
import javax.validation.Valid;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Service
public class TransitPointService {

    private final TransitPointMapper transitPointMapper;

    @GetMapping("/{id}")
    public Optional<TransitPoint> findTransitPoint(@PathVariable long id) {
        return Optional.ofNullable(transitPointMapper.getEntityById(id));
    }

    @PostMapping("/")
    public ResponseEntity createTransitPoint(@Valid @RequestBody TransitPointRequest request) {
        transitPointMapper.createEntity(new TransitPoint(request.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public TransitPoint updateTransitPoint(@PathVariable long id, @Valid @RequestBody TransitPointRequest request) {
        transitPointMapper.updateEntity(new TransitPoint(id));
        return transitPointMapper.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTransitPoint(@PathVariable long id) {
        transitPointMapper.removeEntity(id);
        return ResponseEntity.noContent().build();
    }
}
