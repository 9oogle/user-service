package com.goggles.user_service.instructor.infrastructure.repository;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.entity.InstructorId;
import com.goggles.user_service.instructor.domain.entity.InstructorStatus;
import com.goggles.user_service.instructor.domain.repository.InstructorRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaInstructorRepository implements InstructorRepository {

  private final SpringDataInstructorRepository repository;

  @Override
  public Optional<Instructor> findById(UUID id) {
    return repository.findById(InstructorId.of(id));
  }

  @Override
  public boolean existsByUserId(UUID userId) {
    return repository.existsByUserId_UserId(userId.toString());
  }

  @Override
  public Instructor save(Instructor instructor) {
    return repository.save(instructor);
  }

  @Override
  public Page<Instructor> findAllByStatus(InstructorStatus status, Pageable pageable) {
    return repository.findAllByStatus(status, pageable);
  }

  @Override
  public Page<Instructor> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
