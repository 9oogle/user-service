package com.goggles.user_service.instructor.application.dto;

import com.goggles.user_service.user.domain.entity.Role;
import java.util.UUID;

public record InstructorApproveCommand(UUID instructorId, UUID requesterId, Role requesterRole) {}
