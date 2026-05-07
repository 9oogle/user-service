package com.goggles.user_service.instructor.infrastructure.repository;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.entity.InstructorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInstructorRepository extends JpaRepository<Instructor, InstructorId> {

  boolean existsByUserId_UserId(String userId);
}
