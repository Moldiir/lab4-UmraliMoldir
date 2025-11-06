package org.example.lab4um.service;

import org.example.lab4um.entity.Operators;
import org.example.lab4um.repository.OperatorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorsService {

    private final OperatorsRepository repo;

    public OperatorsService(OperatorsRepository repo) {
        this.repo = repo;
    }

    public List<Operators> getAll() {
        return repo.findAll();
    }

    public List<Operators> getByIds(List<Long> ids) {
        return repo.findAllById(ids);
    }


}
