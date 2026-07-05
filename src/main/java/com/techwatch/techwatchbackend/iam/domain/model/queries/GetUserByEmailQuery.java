package com.techwatch.techwatchbackend.iam.domain.model.queries;

import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;

public record GetUserByEmailQuery(EmailAddress email) {
    public GetUserByEmailQuery {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
    }
}
