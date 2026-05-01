package com.goggles.user_service.user.domain.entity;

import com.goggles.user_service.user.domain.exception.InvalidUserIdException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId {
    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    private UserId(String userId){
        validate(userId);
        this.userId = userId;
    }

    public static UserId of(UUID keycloakId){
        if (keycloakId == null) {
            throw new InvalidUserIdException(null);
        }
        return new UserId(keycloakId.toString());
    }

    private void validate(String userId){
        if(userId == null || userId.isBlank()){
            throw new InvalidUserIdException(userId);
        }
    }
}
