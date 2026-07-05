package com.techwatch.techwatchbackend.iam.interfaces.rest.transform;

import com.techwatch.techwatchbackend.iam.domain.model.commands.SignUpCommand;
import com.techwatch.techwatchbackend.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.email(), resource.password(), resource.firstName(), resource.lastName());
    }
}
