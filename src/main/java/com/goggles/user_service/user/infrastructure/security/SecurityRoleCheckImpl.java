package com.goggles.user_service.user.infrastructure.security;

import com.goggles.user_service.common.domain.service.RoleCheck;
import com.goggles.user_service.user.domain.entity.Role;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SecurityRoleCheckImpl implements RoleCheck {
  @Override
  public boolean hasRole(List<Role> roles) {
    return false;
    // todo roleCheck 구현
  }

  @Override
  public boolean isMine() {
    return false;
  }
}
