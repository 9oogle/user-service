package com.goggles.user_service.instructor.domain.repository;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import java.util.Optional;
import java.util.UUID;

public interface InstructorRepository {

  Optional<Instructor> findById(UUID id);

  boolean existsByUserId(UUID userId);

  Instructor save(Instructor instructor);
}
