package com.goggles.user_service.instructor.application.service;

import com.goggles.user_service.instructor.application.dto.InstructorApplyCommand;
import com.goggles.user_service.instructor.application.dto.InstructorApplyResult;
import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.exception.DuplicateInstructorException;
import com.goggles.user_service.instructor.domain.exception.InstructorForbiddenException;
import com.goggles.user_service.instructor.domain.repository.InstructorRepository;
import com.goggles.user_service.user.domain.entity.Role;
import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.exception.UserNotFoundException;
import com.goggles.user_service.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {

  private final InstructorRepository instructorRepository;
  private final UserRepository userRepository;

  @Transactional
  public InstructorApplyResult apply(InstructorApplyCommand command) {
    User requester =
        userRepository
            .findById(command.requestId())
            .orElseThrow(() -> new UserNotFoundException("user.notfound"));

    if (requester.getRole() != Role.STUDENT) {
      throw new InstructorForbiddenException("instructor.exception.apply.forbidden");
    }
    if (instructorRepository.existsByUserId(command.requestId())) {
      throw new DuplicateInstructorException("instructor.exception.apply.duplicate");
    }

    Instructor instructor = command.toInstructor();
    instructorRepository.save(instructor);
    return InstructorApplyResult.from(instructor);
  }
}
