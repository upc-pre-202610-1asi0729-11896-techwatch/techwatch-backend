package com.techwatch.techwatchbackend.iam.interfaces.rest.transform;

import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(entity.getId(), entity.getEmail().address(), entity.getRole().name());
    }
}
