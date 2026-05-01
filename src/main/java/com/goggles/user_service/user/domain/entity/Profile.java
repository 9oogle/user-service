package com.goggles.user_service.user.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Interest> interests;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Job> jobs;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> educations;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> majors;

    private Profile(List<Interest> interests, List<Job> jobs,
                    List<String> educations, List<String> majors) {
        this.interests  = interests  != null ? List.copyOf(interests)  : List.of();
        this.jobs       = jobs       != null ? List.copyOf(jobs)       : List.of();
        this.educations = educations != null ? List.copyOf(educations) : List.of();
        this.majors     = majors     != null ? List.copyOf(majors)     : List.of();
    }

    public static Profile of(List<Interest> interests, List<Job> jobs,
                             List<String> educations, List<String> majors) {
        return new Profile(interests, jobs, educations, majors);
    }

    public List<Interest> getInterests() {
        return List.copyOf(interests);
    }

    public List<Job> getJobs() {
        return List.copyOf(jobs);
    }

    public List<String> getEducations() {
        return List.copyOf(educations);
    }

    public List<String> getMajors() {
        return List.copyOf(majors);
    }
}