package org.example.lab4um.repository;

import org.example.lab4um.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findByHandled(boolean handled);
}
