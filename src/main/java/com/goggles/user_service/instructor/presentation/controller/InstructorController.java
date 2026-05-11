package com.goggles.user_service.instructor.presentation.controller;

import com.goggles.user_service.instructor.application.dto.InstructorApproveCommand;
import com.goggles.user_service.instructor.application.service.InstructorService;
import com.goggles.user_service.instructor.presentation.dto.InstructorApplyRequest;
import com.goggles.user_service.instructor.presentation.dto.InstructorApplyResponse;
import com.goggles.user_service.instructor.presentation.dto.InstructorApproveResponse;
import com.goggles.user_service.user.domain.entity.Role;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructors")
public class InstructorController {

  private final InstructorService instructorService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public InstructorApplyResponse apply(
      @RequestHeader("X-User-Id") String requesterId,
      @Valid @RequestBody InstructorApplyRequest request) {

    return InstructorApplyResponse.from(
        instructorService.apply(request.toServiceRequest(UUID.fromString(requesterId))));
  }

  @PatchMapping("/{instructorId}/approve")
  public InstructorApproveResponse approve(
      @PathVariable UUID instructorId,
      @RequestHeader("X-User-Id") UUID requesterId,
      @RequestHeader("X-User-Role") Role requesterRole) {
    return InstructorApproveResponse.from(
        instructorService.approve(
            new InstructorApproveCommand(instructorId, requesterId, requesterRole)));
  }

  @PatchMapping("/{instructorId}/reject")
  public InstructorApproveResponse reject(
      @PathVariable UUID instructorId,
      @RequestHeader("X-User-Id") UUID requesterId,
      @RequestHeader("X-User-Role") Role requesterRole) {
    return InstructorApproveResponse.from(
        instructorService.reject(
            new InstructorApproveCommand(instructorId, requesterId, requesterRole)));
  }
}
