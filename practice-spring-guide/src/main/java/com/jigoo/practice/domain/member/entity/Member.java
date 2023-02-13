package com.jigoo.practice.domain.member.entity;

import com.jigoo.practice.domain.common.BaseEntity;
import com.jigoo.practice.domain.model.Email;
import com.jigoo.practice.domain.model.Name;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(
            name = "email",
            unique = true,
            updatable = false,
            nullable = false,
            length = 50
    ))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(
            name = "referral_code",
            unique = true,
            updatable = false,
            length = 50
    ))
    private ReferralCode referralCode;

    @Embedded
    private Name name;

    @Embedded
    private Password password;


    @Builder
    public Member(Email email, Name name, Password password, ReferralCode referralCode) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.referralCode = referralCode;
    }

    public static Member createMember(Member member) {
        return Member.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .referralCode(member.getReferralCode())
                .build();
    }

}
