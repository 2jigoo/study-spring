package com.jigoo.practice.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    @Column(name = "password", length = 255, nullable = false)
    private String value;

    public Password(String value) {
        this.value = value;
    }

    public static Password of(String password) {
        return new Password(password);
    }

}
