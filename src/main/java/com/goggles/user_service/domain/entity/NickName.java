package com.goggles.user_service.domain.entity;

import com.goggles.user_service.domain.exception.InvaliNickNameException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NickName {

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    private NickName(String nickName){

        this.nickName = nickName;
    }

    public static NickName of(String nickName){
        return new NickName(nickName);
    }

    private void validate(String nickName){
        if (nickName == null || nickName.isBlank()) {
            throw new InvaliNickNameException(nickName);
        }

        if (nickName.length() < 2 || nickName.length() > 15) {
            throw new InvaliNickNameException(nickName);
        }

        if (!nickName.matches("^[a-zA-Z0-9가-힣]+$")) {
            throw new InvaliNickNameException(nickName);
        }
    }
}