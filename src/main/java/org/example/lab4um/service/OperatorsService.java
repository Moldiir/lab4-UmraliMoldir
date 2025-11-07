package org.example.lab4um.service;

import org.example.lab4um.entity.Operators;
import org.example.lab4um.repository.OperatorsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OperatorsService {

    private final OperatorsRepository repo;

    public OperatorsService(OperatorsRepository repo) {
        this.repo = repo;
    }

    public List<Operators> getAll() {
        return repo.findAll();
    }

    public Operators getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Operators> getByIds(List<Long> ids) {
        return repo.findAllById(ids);
    }

    @Transactional
    public Operators addOperator(Operators o) {
        return repo.save(o);
    }
}
