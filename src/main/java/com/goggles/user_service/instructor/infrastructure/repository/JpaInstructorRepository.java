package com.goggles.user_service.instructor.infrastructure.repository;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.repository.InstructorRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaInstructorRepository implements InstructorRepository {

  private final SpringDataInstructorRepository repository;

  @Override
  public Optional<Instructor> findById(UUID id) {
    return Optional.empty();
  }

  @Override
  public boolean existsByUserId(UUID userId) {
    return repository.existsByUserId_UserId(userId.toString());
  }

  @Override
  public Instructor save(Instructor instructor) {
    return repository.save(instructor);
  }
}
