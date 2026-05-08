package com.goggles.user_service.user.infrastructure.security;

import com.goggles.user_service.common.domain.service.RoleCheck;
import com.goggles.user_service.user.domain.entity.Role;
import java.util.UUID;

public class SecurityRoleCheckImpl implements RoleCheck {

  private final UUID requesterId;
  private final Role requesterRole;
  private final UUID targetUserId;

  public SecurityRoleCheckImpl(UUID requesterId, Role requesterRole, UUID targetUserId) {
    this.requesterId = requesterId;
    this.requesterRole = requesterRole;
    this.targetUserId = targetUserId;
  }

  @Override
  public boolean hasRole(Role role) {
    if (role == null) return false;
    return this.requesterRole == role;
  }

  @Override
  public boolean isMine() {
    return requesterId.equals(targetUserId);
  }
}
