package com.goggles.user_service.domain.entity;

import com.goggles.user_service.domain.exception.InvalidPhoneNumberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

    @Column(name ="phone_number", nullable = false, unique = true)
    private String phoneNumber;

    private PhoneNumber(String phoneNumber){
        validate(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public static PhoneNumber of(String phoneNumber){
        return new PhoneNumber(phoneNumber);
    }

    private void validate(String phoneNumber){
        if(phoneNumber == null || !phoneNumber.matches("^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$")){
            throw new InvalidPhoneNumberException(phoneNumber);
        }
    }
}
