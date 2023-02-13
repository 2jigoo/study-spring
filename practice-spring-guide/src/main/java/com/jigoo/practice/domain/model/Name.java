package com.jigoo.practice.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    @Column(name = "first_name", length = 50, nullable = false)
    private String first;

    @Column(name = "middle_name", length = 50)
    private String middle;

    @Column(name = "last_name", length = 50, nullable = false)
    private String last;

    @Builder
    public Name(String first, String middle, String last) {
        this.first = first;
        this.middle = StringUtils.hasText(middle) ? middle : null;
        this.last = last;
    }

    public static Name of(String first, String middle, String last) {
        return Name.builder()
                .first(first)
                .middle(middle)
                .last(last)
                .build();
    }

    public String getFullName() {
        if (this.middle == null) {
            return String.format("%s %s", this.first, this.last);
        }

        return String.format("%s %s %s", this.first, this.middle, this.last);
    }

}
