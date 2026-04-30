package com.goggles.user_service.instructor.domain.entity;

import com.goggles.common.domain.BaseTime;
import com.goggles.common.exception.BadRequestException;
import com.goggles.common.exception.ForbiddenException;
import com.goggles.user_service.user.domain.entity.Role;
import com.goggles.user_service.user.domain.entity.UserId;
import com.goggles.user_service.common.domain.service.RoleCheck;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(name = "P_INSTRUCTORS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instructor extends BaseTime {

    @EmbeddedId
    private InstructorId id;

    @Embedded
    private UserId userId;

    @Enumerated(EnumType.STRING)
    private InstructorStatus status;

    @Embedded
    private BankAccount bankAccount;

    @Embedded
    private InstructorDescription instructorDescription;

    @Column
    private LocalDateTime approvedAt;

    @Builder
    public Instructor (
            UUID userId,
            String field, String bio, String career, String portfolio
    ) {
        this.userId = UserId.of(userId);
        this.id = InstructorId.generate();
        this.status = InstructorStatus.REQUESTED;
        this.instructorDescription = InstructorDescription.of(field,bio,career,portfolio);
    }

    public void approve(RoleCheck roleCheck) {
        checkMasterOnly(roleCheck);
        if (this.status != InstructorStatus.REQUESTED) {
            throw new BadRequestException("instructor.exception.approve.invalid");
        }
        this.status = InstructorStatus.APPROVED;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(RoleCheck roleCheck) {
        checkMasterOnly(roleCheck);
        if (this.status != InstructorStatus.REQUESTED) {
            throw new BadRequestException("instructor.exception.reject.invalid");
        }
        this.status = InstructorStatus.REJECTED;
    }

    public void registerBankAccount(Bank bankName, String accountNumber,
                                    String accountHolder, RoleCheck roleCheck) {
        checkMasterOnly(roleCheck);
        this.bankAccount = BankAccount.of(bankName, accountNumber, accountHolder);
    }

    public void changeInstructorDescription(String filed, String bio, String career, String portfolio,
                                            RoleCheck roleCheck){
        checkMasterOnly(roleCheck);

        if(this.instructorDescription.getField().equals(filed)
        && this.instructorDescription.getBio().equals(bio)
        && this.instructorDescription.getCareer().equals(career)
        && this.instructorDescription.getPortfolio().equals(portfolio)){
            return;
        }

        this.instructorDescription = InstructorDescription.of(filed, bio, career, portfolio);
    }

    private void checkMasterOnly(RoleCheck roleCheck) {
        if (!roleCheck.hasRole(Role.MASTER)) {
            throw new ForbiddenException("instructor.exception.master.forbidden");
        }
    }
}
