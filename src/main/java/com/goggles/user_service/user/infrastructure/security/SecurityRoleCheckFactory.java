package com.goggles.user_service.user.infrastructure.security;

import com.goggles.user_service.common.domain.service.RoleCheck;
import com.goggles.user_service.common.domain.service.RoleCheckFactory;
import com.goggles.user_service.user.domain.entity.Role;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SecurityRoleCheckFactory implements RoleCheckFactory {

  @Override
  public RoleCheck create(UUID requesterId, Role requesterRole, UUID targetUserId) {
    return new SecurityRoleCheckImpl(requesterId, requesterRole, targetUserId);
  }
}
