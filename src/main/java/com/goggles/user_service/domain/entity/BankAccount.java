package com.goggles.user_service.domain.entity;

import com.goggles.user_service.domain.exception.InvalidBankAccountException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccount {

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    private BankAccount(String bankName, String accountNumber, String accountHolder){
        validate(accountNumber, accountHolder);
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    public static BankAccount of(String bankName, String accountNumber, String accountHolder){
        return new BankAccount(bankName, accountNumber, accountHolder);
    }

    private void validate(String accountNumber, String accountHolder){
        if (accountNumber == null || !accountNumber.matches("^[0-9\\-]{10,20}$")) {
            throw new InvalidBankAccountException(accountNumber);
        }
        if (accountHolder == null || !accountHolder.matches("^[가-힣]{2,5}$")) {
            throw new InvalidBankAccountException(accountHolder);
        }
    }
}
