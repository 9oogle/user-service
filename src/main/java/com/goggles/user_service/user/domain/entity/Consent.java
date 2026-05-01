package com.goggles.user_service.user.domain.entity;

import com.goggles.common.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consent {
    @Column(name="personal_consent")
    private boolean personal; // 개인정보 사용 동의

    @Column(name="marketing_consent")
    private boolean marketing; // 마케팅 정보로 활용 동의

    @Column(name="email_consent")
    private boolean email; // 이메일 수신 동의

    private Consent(boolean personal, boolean marketing, boolean email) {
        // 개인정보 수집 필수 체크
        if (!personal) {
            throw new BadRequestException("consent Exception");
        }

        this.personal = personal;
        this.marketing = marketing;
        this.email = email;
    }

    public static Consent of(boolean personalInfoConsent, boolean marketingConsent, boolean emailConsent) {
        return new Consent(personalInfoConsent, marketingConsent, emailConsent);
    }
}
