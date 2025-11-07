package org.example.lab4um.service;

import org.example.lab4um.entity.Courses;
import org.example.lab4um.repository.CoursesRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CoursesService {

    private final CoursesRepository repo;

    public CoursesService(CoursesRepository repo) {
        this.repo = repo;
    }

    public List<Courses> getAll() {
        return repo.findAll();
    }

    public Courses getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public Courses addCourse(Courses c) {
        return repo.save(c);
    }

    @Transactional
    public boolean deleteCourse(Long id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
