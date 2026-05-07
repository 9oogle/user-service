package com.goggles.user_service.instructor.presentation.controller;

import com.goggles.user_service.instructor.application.service.InstructorService;
import com.goggles.user_service.instructor.presentation.dto.InstructorApplyRequest;
import com.goggles.user_service.instructor.presentation.dto.InstructorApplyResponse;
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
}
