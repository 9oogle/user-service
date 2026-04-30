package com.goggles.user_service.user.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@ToString
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

    private Profile(List<Interest> interests, List<Job> jobs, List<String> educations, List<String> majors) {
        this.interests = interests;
        this.jobs = jobs;
        this.educations = educations;
        this.majors = majors;
    }

    public static Profile of(List<Interest> interests, List<Job> jobs, List<String> educations, List<String> majors){
        return new Profile(interests,jobs, educations, majors);
    }

}
