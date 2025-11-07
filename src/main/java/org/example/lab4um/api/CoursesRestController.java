package org.example.lab4um.api;

import lombok.RequiredArgsConstructor;
import org.example.lab4um.entity.Courses;
import org.example.lab4um.service.CoursesService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CoursesRestController {

    private final CoursesService courses;

    @GetMapping
    public ResponseEntity<?> all() {
        List<Courses> list = courses.getAll();
        return list.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Courses body) {
        Courses created = courses.addCourse(body);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return courses.deleteCourse(id)
                ? ResponseEntity.ok("Course deleted successfully")
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
