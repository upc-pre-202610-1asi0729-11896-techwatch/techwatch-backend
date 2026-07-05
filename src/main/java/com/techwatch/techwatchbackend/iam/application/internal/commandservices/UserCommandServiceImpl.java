package com.techwatch.techwatchbackend.iam.application.internal.commandservices;

import com.techwatch.techwatchbackend.iam.application.commandservices.UserCommandService;
import com.techwatch.techwatchbackend.iam.application.internal.outboundservices.hashing.HashingService;
import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.commands.SignUpCommand;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;
import com.techwatch.techwatchbackend.iam.domain.repositories.UserRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
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
}
