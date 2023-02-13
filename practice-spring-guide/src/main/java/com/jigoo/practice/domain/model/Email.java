package com.jigoo.practice.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = { "value" })
@Embeddable
public class Email {

    @Column(name = "email", length = 50)
    @javax.validation.constraints.Email
    @NotEmpty
    private String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email of(String value) {
        return new Email();
    }

    public String getHost() {
        int idx = value.indexOf("@");
        return idx == -1 ? null : value.substring(idx + 1);
    }

    public String getId() {
        final int idx = value.indexOf("@");
        return idx == -1 ? null : value.substring(0, idx);
    }

}
