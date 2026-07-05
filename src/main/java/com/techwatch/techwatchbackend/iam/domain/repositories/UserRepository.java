package com.techwatch.techwatchbackend.iam.domain.repositories;

import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(EmailAddress email);
    boolean existsByEmail(EmailAddress email);
    User save(User user);
}
