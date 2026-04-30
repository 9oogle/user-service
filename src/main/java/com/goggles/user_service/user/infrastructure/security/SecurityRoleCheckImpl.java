package com.goggles.user_service.user.infrastructure.security;

import com.goggles.user_service.user.domain.entity.Role;
import com.goggles.user_service.user.domain.service.RoleCheck;

import java.util.List;

public class SecurityRoleCheckImpl implements RoleCheck {
    @Override
    public boolean hasRole(List<Role> roles) {
        return false;
        //todo roleCheck 구현
    }

    @Override
    public boolean isMine() {
        return false;
    }
}
