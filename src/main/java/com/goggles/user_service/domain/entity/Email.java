package com.goggles.user_service.domain.entity;

import com.goggles.user_service.domain.exception.InvalidEmailExceprion;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Email {
    @Column(nullable = false, unique = true)
    private String email;

    private Email(String email){
        validate(email);
    }

    public static Email of(String email){
        return new Email(email);
    }

    private void validate(String email){
        if(email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
            throw new InvalidEmailExceprion(email);
        }
    }

    public String getEmail(){
        return email;
    }
}
