package org.example.lab4um.api;

import org.example.lab4um.entity.ApplicationRequest;
import org.example.lab4um.entity.Operators;
import org.example.lab4um.service.ApplicationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.example.lab4um.service.OperatorsService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operators")
public class OperatorsRestController {

    private final OperatorsService operators;
    private final ApplicationRequestService requests;

    @GetMapping
    public ResponseEntity<?> all() {
        List<Operators> list = operators.getAll();   // <-- вместо getAllOperators()
        return list.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(list);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody Operators body) {
        Operators created = operators.addOperator(body);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Назначить оператора на заявку и вернуть обновлённую заявку
    @PutMapping("/{id}/assign/{requestId}")
    public ResponseEntity<?> assign(@PathVariable Long id, @PathVariable Long requestId) {
        ApplicationRequest updated = requests.assignOperator(id, requestId);
        return (updated == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(updated);
    }
}
