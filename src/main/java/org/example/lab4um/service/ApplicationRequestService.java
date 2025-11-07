    package org.example.lab4um.service;

    import org.example.lab4um.entity.ApplicationRequest;
    import org.example.lab4um.entity.Operators;
    import org.example.lab4um.repository.ApplicationRequestRepository;
    import org.example.lab4um.repository.OperatorsRepository;
    import org.springframework.dao.EmptyResultDataAccessException;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    @Service
    @Transactional(readOnly = true)
    public class ApplicationRequestService {

        private final ApplicationRequestRepository repository;
        private final OperatorsRepository operatorsRepository;

        public ApplicationRequestService(ApplicationRequestRepository repository,
                                         OperatorsRepository operatorsRepository) {
            this.repository = repository;
            this.operatorsRepository = operatorsRepository;
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

        @Transactional
        public ApplicationRequest save(ApplicationRequest request) {
            return repository.save(request);
        }

        @Transactional
        public ApplicationRequest addRequest(ApplicationRequest request) {
            return repository.save(request);
        }

        public ApplicationRequest getById(Long id) {
            return repository.findById(id).orElse(null);
        }

        @Transactional
        public ApplicationRequest updateRequest(Long id, ApplicationRequest body) {
            ApplicationRequest existing = getById(id);
            if (existing == null) return null;

            if (body.getUserName() != null) existing.setUserName(body.getUserName());
            if (body.getPhone() != null) existing.setPhone(body.getPhone());
            if (body.getCommentary() != null) existing.setCommentary(body.getCommentary());


            existing.setHandled(body.isHandled());

            if (body.getCourse() != null) {
                existing.setCourse(body.getCourse());
            }

            return repository.save(existing);
        }

        @Transactional
        public boolean deleteRequest(Long id) {
            try {
                repository.deleteById(id);
                return true;
            } catch (EmptyResultDataAccessException ex) {
                return false;
            }
        }

        @Transactional
        public ApplicationRequest assignOperator(Long operatorId, Long requestId) {
            Operators op = operatorsRepository.findById(operatorId).orElse(null);
            ApplicationRequest req = repository.findById(requestId).orElse(null);
            if (op == null || req == null) return null;

            req.setOperator(op);

            req.setHandled(true);

            return repository.save(req);
        }

        @Transactional
        public void delete(Long id) {
            repository.deleteById(id);
        }
    }
