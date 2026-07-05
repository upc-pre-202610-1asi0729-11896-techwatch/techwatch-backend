package com.techwatch.techwatchbackend.iam.application.internal.queryservices;

import com.techwatch.techwatchbackend.iam.application.queryservices.UserQueryService;
import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.queries.GetUserByEmailQuery;
import com.techwatch.techwatchbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.techwatch.techwatchbackend.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }
}
