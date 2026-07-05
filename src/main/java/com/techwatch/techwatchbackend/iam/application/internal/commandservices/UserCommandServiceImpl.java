package com.techwatch.techwatchbackend.iam.application.internal.commandservices;

import com.techwatch.techwatchbackend.iam.application.commandservices.UserCommandService;
import com.techwatch.techwatchbackend.iam.application.internal.outboundservices.hashing.HashingService;
import com.techwatch.techwatchbackend.iam.application.internal.outboundservices.tokens.TokenService;
import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.commands.SignInCommand;
import com.techwatch.techwatchbackend.iam.domain.model.commands.SignUpCommand;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;
import com.techwatch.techwatchbackend.iam.domain.repositories.UserRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService,
                                  TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Result<Long, ApplicationError> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(new EmailAddress(command.email()))) {
            return Result.failure(ApplicationError.conflict("User",
                    "Email '%s' is already registered".formatted(command.email())));
        }
        var user = new User(command, hashingService.encode(command.password()));
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("sign-up", e.getMessage()));
        }
        return Result.success(user.getId());
    }

    @Override
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command) {
        var user = userRepository.findByEmail(new EmailAddress(command.email()));
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.email()));
        }
        if (!hashingService.matches(command.password(), user.get().getPasswordHash())) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid email or password"));
        }
        var token = tokenService.generateToken(user.get().getEmail().address());
        return Result.success(ImmutablePair.of(user.get(), token));
    }
}
