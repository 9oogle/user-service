package com.goggles.user_service.user.domain.entity;

import com.goggles.common.domain.BaseTime;
import com.goggles.common.exception.BadRequestException;
import com.goggles.common.exception.ForbiddenException;
import com.goggles.user_service.common.domain.service.RoleCheck;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(name = "P_USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    private final long MIN_AGE = 14;

    @EmbeddedId
    private UserId id;

    @Embedded
    private Email email;

    @Embedded
    private Name name;

    @Embedded
    private NickName nickName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private Consent consent;

    @Embedded
    private Profile profile;


    @Builder
    public User(
            UUID keycloakId,
            String email,
            String name,
            String nickName,
            Gender gender,
            String phoneNumber,
            LocalDate birthDate,
            boolean personalInfoConsent,
            boolean marketingConsent,
            boolean emailConsent,
            List<Interest> interests,
            List<Job> jobs,
            List<String> educations,
            List<String> majors
            ) {
        this.id = UserId.of(keycloakId);
        this.email = Email.of(email);
        this.name = Name.of(name);
        this.nickName = NickName.of(nickName);
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = Role.STUDENT;       // 기본값 강제
        this.status = UserStatus.ACTIVE; // 기본값 강제
        this.consent = Consent.of(personalInfoConsent, marketingConsent, emailConsent);
        this.profile = Profile.of(interests,jobs, educations, majors);
    }

    private void validateBasicInfo(){
        if(this.gender == null){
            throw new BadRequestException("user.validation.gender.required");
        }

        if(this.birthDate == null){
            throw new BadRequestException("user.validation.birthdate.required");
        }

        if(ChronoUnit.YEARS.between(this.birthDate, LocalDate.now())< MIN_AGE){
            throw new BadRequestException("user.validation.birthdate.invalid");
        }
    }

    public void changeRole(Role role, RoleCheck roleCheck){
        if(this.role == role){
            return;
        }
        checkMasterOnly(roleCheck);

        this.role = role;
        //todo 상태변경 갱신 이벤트 발행 구현
    }

    public void changeBasicInfo(String name, String nickName, LocalDate birthDate,
                                RoleCheck roleCheck) {

        if (name.equals(this.name.getName()) && nickName.equals(this.nickName.getNickName())
                && birthDate.equals(this.birthDate)) {
            return;
        }

        checkMine(roleCheck);

        this.name = Name.of(name);
        this.nickName = NickName.of(nickName);
        this.birthDate = birthDate;
        //todo 상태변경 갱신 이벤트 발행 구현
    }

    public void changePhoneNumber(String phoneNumber, RoleCheck roleCheck){
        if(phoneNumber.equals(this.phoneNumber.getPhoneNumber())){
            return;
        }

        checkMine(roleCheck);

        this.phoneNumber = PhoneNumber.of(phoneNumber);
    }

    public void changeConsent(boolean marketing, boolean email, RoleCheck roleCheck){
        if(marketing == this.consent.isMarketing() && email == this.consent.isEmail()){
            return;
        }
        checkMine(roleCheck);

        this.consent = Consent.of(consent.isPersonal(), marketing, email);
    }

    public void changeProfile(List<Interest> interests, List<Job> jobs,
                              List<String> educations, List<String> majors,
                              RoleCheck roleCheck){

        checkMine(roleCheck);

        this.profile = Profile.of(interests, jobs, educations, majors);
    }

    public void userDelete(RoleCheck roleCheck) {
        checkMine(roleCheck);
        if (this.status == UserStatus.DELETED) {
            throw new BadRequestException("user.exception.already.deleted");
        }
        this.status = UserStatus.DELETED;
    }

    private void checkMasterOnly(RoleCheck roleCheck) {
        if (!roleCheck.hasRole(Role.MASTER)) {
            throw new ForbiddenException("user.exception.master.forbidden");
        }
    }

    private void checkMine(RoleCheck roleCheck) {
        if (!roleCheck.hasRole(Role.MASTER) && !roleCheck.isMine()) {
            throw new ForbiddenException("user.exception.mine.forbidden");
        }
    }
}
