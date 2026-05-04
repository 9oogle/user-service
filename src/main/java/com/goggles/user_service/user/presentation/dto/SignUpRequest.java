package com.goggles.user_service.user.presentation.dto;

import com.goggles.user_service.user.application.dto.SignUpCommand;
import com.goggles.user_service.user.domain.entity.Gender;
import com.goggles.user_service.user.domain.entity.Interest;
import com.goggles.user_service.user.domain.entity.Job;

import java.time.LocalDate;
import java.util.List;

public record SignUpRequest(
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthDate,
            String phoneNumber,
            boolean personalInfoConsent,
            boolean marketingConsent,
            boolean emailConsent,
            List<Interest> interests,
            List<Job> jobs,
            List<String> educations,
            List<String> majors
    ){
        public SignUpCommand toServiceRequest() {
            return SignUpCommand.builder()
                    .email(this.email)
                    .password(this.password)
                    .name(this.name)
                    .nickName(this.nickname)
                    .gender(this.gender)
                    .birthDate(this.birthDate)
                    .phoneNumber(this.phoneNumber)
                    .personalInfoConsent(this.personalInfoConsent)
                    .marketingConsent(this.marketingConsent)
                    .emailConsent(this.emailConsent)
                    .interests(this.interests)
                    .jobs(this.jobs)
                    .educations(this.educations)
                    .majors(this.majors)
                    .build();
        }
}