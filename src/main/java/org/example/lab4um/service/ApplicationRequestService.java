package org.example.lab4um.service;

import org.example.lab4um.entity.ApplicationRequest;
import org.example.lab4um.repository.ApplicationRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRequestService {
    private final ApplicationRequestRepository repository;

    public ApplicationRequestService(ApplicationRequestRepository repository) {
        this.repository = repository;
    }

    public List<ApplicationRequest> getAll() {
        return repository.findAll();
    }

    public List<ApplicationRequest> getPending() {
        return repository.findByHandled(false);
    }

    public List<ApplicationRequest> getProcessed() {
        return repository.findByHandled(true);
    }

    public ApplicationRequest save(ApplicationRequest request) {
        return repository.save(request);
    }

    public ApplicationRequest getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
