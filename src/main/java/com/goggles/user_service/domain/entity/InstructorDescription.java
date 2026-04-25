package com.goggles.user_service.domain.entity;

import com.goggles.user_service.domain.exception.InvalidInstructorDescriptionException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InstructorDescription {

    @Column(nullable = false, length = 500)
    private String bio;

    @Column(nullable = false, length = 1000)
    private String career;

    @Column(nullable = false, length = 500)
    private String portfolio;

    private InstructorDescription(String bio, String career, String portfolio){
        validate(bio, career, portfolio);
        this.bio = bio;
        this.career = career;
        this.portfolio = portfolio;
    }

    public static InstructorDescription of(String bio, String career, String portfolio){
        return new InstructorDescription(bio, career, portfolio);
    }

    private void validate(String bio, String career, String portfolio){
        if (bio == null || bio.isBlank()) {
            throw new InvalidInstructorDescriptionException("bio");
        }
        if (career == null || career.isBlank()) {
            throw new InvalidInstructorDescriptionException("career");
        }
        if (portfolio == null || portfolio.isBlank()) {
            throw new InvalidInstructorDescriptionException("portfolio");
        }
    }
}