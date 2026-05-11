package com.goggles.user_service.instructor.application.service;

import com.goggles.user_service.common.domain.service.RoleCheck;
import com.goggles.user_service.common.domain.service.RoleCheckFactory;
import com.goggles.user_service.instructor.application.dto.InstructorApplyCommand;
import com.goggles.user_service.instructor.application.dto.InstructorApplyResult;
import com.goggles.user_service.instructor.application.dto.InstructorApproveCommand;
import com.goggles.user_service.instructor.application.dto.InstructorApproveResult;
import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.exception.DuplicateInstructorException;
import com.goggles.user_service.instructor.domain.exception.InstructorNotFoundException;
import com.goggles.user_service.instructor.domain.repository.InstructorRepository;
import com.goggles.user_service.user.domain.entity.Role;
import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.exception.UserNotFoundException;
import com.goggles.user_service.user.domain.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {

  private final InstructorRepository instructorRepository;
  private final UserRepository userRepository;
  private final RoleCheckFactory roleCheckFactory;

  @Transactional
  public InstructorApplyResult apply(InstructorApplyCommand command) {
    User requester =
        userRepository
            .findById(command.requestId())
            .orElseThrow(() -> new UserNotFoundException("user.notfound"));

    requester.validateCanApplyAsInstructor();

    if (instructorRepository.existsByUserId(command.requestId())) {
      throw new DuplicateInstructorException("instructor.exception.apply.duplicate");
    }

    Instructor instructor = command.toInstructor();
    Instructor saved = instructorRepository.save(instructor);
    return InstructorApplyResult.from(saved);
  }

  @Transactional
  public InstructorApproveResult approve(InstructorApproveCommand command) {
    Instructor instructor =
        instructorRepository
            .findById(command.instructorId())
            .orElseThrow(() -> new InstructorNotFoundException("instructor.notfound"));

    User user =
        userRepository
            .findById(UUID.fromString(instructor.getUserId().getUserId()))
            .orElseThrow(() -> new UserNotFoundException("user.notfound"));

    RoleCheck roleCheck =
        roleCheckFactory.create(command.requesterId(), command.requesterRole(), null);

    instructor.approve(roleCheck);
    user.changeRole(Role.INSTRUCTOR, roleCheck);

    return InstructorApproveResult.from(instructor);
  }

  @Transactional
  public InstructorApproveResult reject(InstructorApproveCommand command) {
    Instructor instructor =
        instructorRepository
            .findById(command.instructorId())
            .orElseThrow(() -> new InstructorNotFoundException("instructor.notfound"));

    RoleCheck roleCheck =
        roleCheckFactory.create(command.requesterId(), command.requesterRole(), null);

    instructor.reject(roleCheck);
    return InstructorApproveResult.from(instructor);
  }
}
