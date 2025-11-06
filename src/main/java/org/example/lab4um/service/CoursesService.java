package org.example.lab4um.service;

import org.example.lab4um.entity.Courses;
import org.example.lab4um.repository.CoursesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {
    private final CoursesRepository repo;

    public CoursesService(CoursesRepository repo) {
        this.repo = repo;
    }

    public List<Courses> getAll() {
        return repo.findAll();
    }
}
