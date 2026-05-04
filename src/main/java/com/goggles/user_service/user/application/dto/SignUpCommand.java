package com.goggles.user_service.user.application.dto;

import com.goggles.user_service.user.domain.entity.Gender;
import com.goggles.user_service.user.domain.entity.Interest;
import com.goggles.user_service.user.domain.entity.Job;
import com.goggles.user_service.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class SignUpCommand {
    private String email;
    private String password;
    private String name;
    private String nickName;
    private Gender gender;
    private LocalDate birthDate;
    private String phoneNumber;
    private boolean personalInfoConsent;
    private boolean marketingConsent;
    private boolean emailConsent;
    private List<Interest> interests;
    private List<Job> jobs;
    private List<String> educations;
    private List<String> majors;

    public User toUser(UUID keycloakId) {
        return User.builder()
                .keycloakId(keycloakId)
                .email(this.email)
                .name(this.name)
                .nickName(this.nickName)
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
