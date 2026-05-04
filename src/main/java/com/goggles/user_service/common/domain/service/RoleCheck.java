package com.goggles.user_service.common.domain.service;

import com.goggles.user_service.user.domain.entity.Role;
import java.util.List;

public interface RoleCheck {

  boolean hasRole(List<Role> roles);

  default boolean hasRole(Role role) {
    return hasRole(List.of(role));
  }

  boolean isMine();
}
