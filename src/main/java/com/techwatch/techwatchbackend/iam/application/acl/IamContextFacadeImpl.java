package com.techwatch.techwatchbackend.iam.application.acl;

import com.techwatch.techwatchbackend.iam.application.queryservices.UserQueryService;
import com.techwatch.techwatchbackend.iam.domain.model.queries.GetUserByEmailQuery;
import com.techwatch.techwatchbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;
import com.techwatch.techwatchbackend.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Long fetchUserIdByEmail(String email) {
        return userQueryService.handle(new GetUserByEmailQuery(new EmailAddress(email)))
                .map(user -> user.getId())
                .orElse(0L);
    }

    @Override
    public String fetchUserEmailById(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(user -> user.getEmail().address())
                .orElse("");
    }
}
