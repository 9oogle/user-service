package com.goggles.user_service.common.domain.service;

import com.goggles.user_service.user.domain.entity.Role;

public interface RoleCheck {

  boolean hasRole(Role role);

  boolean isMine();
}
