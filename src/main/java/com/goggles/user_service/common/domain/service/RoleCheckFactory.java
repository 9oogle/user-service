package com.goggles.user_service.common.domain.service;

import com.goggles.user_service.user.domain.entity.Role;

import java.util.UUID;

public interface RoleCheckFactory {

  RoleCheck create(UUID requesterId, Role requesterRole, UUID targetUserId);
}
