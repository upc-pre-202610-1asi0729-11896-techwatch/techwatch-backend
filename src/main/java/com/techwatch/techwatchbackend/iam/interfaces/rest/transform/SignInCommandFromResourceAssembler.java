package com.techwatch.techwatchbackend.iam.interfaces.rest.transform;

import com.techwatch.techwatchbackend.iam.domain.model.commands.SignInCommand;
import com.techwatch.techwatchbackend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}
