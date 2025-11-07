package org.example.lab4um.api;

import org.example.lab4um.entity.ApplicationRequest;
import org.example.lab4um.service.ApplicationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class ApplicationRequestRestController {

    private final ApplicationRequestService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ApplicationRequest> list = service.getAll();
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ApplicationRequest r = service.getById(id);
        return (r == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ApplicationRequest body) {
        ApplicationRequest created = service.addRequest(body);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ApplicationRequest body) {
        ApplicationRequest updated = service.updateRequest(id, body);
        return (updated == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteRequest(id) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
