package com.techwatch.techwatchbackend.iam.interfaces.rest;

import com.techwatch.techwatchbackend.iam.application.commandservices.UserCommandService;
import com.techwatch.techwatchbackend.iam.application.queryservices.UserQueryService;
import com.techwatch.techwatchbackend.iam.domain.model.aggregates.User;
import com.techwatch.techwatchbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.techwatch.techwatchbackend.iam.interfaces.rest.resources.SignUpResource;
import com.techwatch.techwatchbackend.iam.interfaces.rest.resources.UserResource;
import com.techwatch.techwatchbackend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes authentication endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication and user registration endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public AuthenticationController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Register a new user. A profile is automatically created for the user (published language
     * UserRegisteredEvent, consumed by the Profiles bounded context).
     *
     * @param resource The {@link SignUpResource} with the registration data
     * @return The created {@link UserResource} resource
     */
    @PostMapping("/sign-up")
    @Operation(summary = "User registration", description = "Creates a new user account and its profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Email already registered")
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpResource resource) {
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command)
                .flatMap(userId -> userQueryService.handle(new GetUserByIdQuery(userId))
                        .<Result<User, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("User", userId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }
}
