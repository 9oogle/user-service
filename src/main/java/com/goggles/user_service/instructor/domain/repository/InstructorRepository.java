package com.goggles.user_service.instructor.domain.repository;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.entity.InstructorStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InstructorRepository {

  Optional<Instructor> findById(UUID id);

  boolean existsByUserId(UUID userId);

  Instructor save(Instructor instructor);

  Page<Instructor> findAllByStatus(InstructorStatus status, Pageable pageable);

  Page<Instructor> findAll(Pageable pageable);
}
