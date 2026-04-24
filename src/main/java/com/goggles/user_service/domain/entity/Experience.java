package com.goggles.user_service.domain.entity;

import com.goggles.user_service.domain.exception.InvalidExperienceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Experience {
    @Column
    private String job;

    @Column
    private String education;

    @Column
    private String major;

    private Experience(String job, String education, String major){
        validate(job, education, major);
        this.job = job;
        this.education = education;
        this.major = major;
    }

    public static Experience of(String job, String education, String major){
        return new Experience(job, education, major);
    }

    private void validate(String job, String education, String major){
        if(job.length() < 2 || job.length() > 20){
            throw new InvalidExperienceException(job);
        }
    }
}
