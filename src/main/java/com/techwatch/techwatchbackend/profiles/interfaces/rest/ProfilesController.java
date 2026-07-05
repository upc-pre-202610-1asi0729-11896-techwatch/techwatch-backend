package com.techwatch.techwatchbackend.profiles.interfaces.rest;

import com.techwatch.techwatchbackend.profiles.application.commandservices.ProfileCommandService;
import com.techwatch.techwatchbackend.profiles.application.queryservices.ProfileQueryService;
import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByIdQuery;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.CreateProfileResource;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.ProfileResource;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.resources.UpdateProfileResource;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.techwatch.techwatchbackend.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes profiles endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile management endpoints")
public class ProfilesController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileCommandService profileCommandService,
                              ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    /**
     * Create a new profile for a user.
     *
     * @param resource The {@link CreateProfileResource} with the profile data
     * @return The created {@link ProfileResource} resource
     */
    @PostMapping
    @Operation(summary = "Create a profile", description = "Creates the profile of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created successfully"),
            @ApiResponse(responseCode = "409", description = "The user already has a profile")
    })
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileResource resource) {
        var command = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = profileCommandService.handle(command)
                .flatMap(profileId -> profileQueryService.handle(new GetProfileByIdQuery(profileId))
                        .<Result<Profile, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(
                                ApplicationError.notFound("Profile", profileId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    /**
     * Get a profile by its id.
     *
     * @param profileId The profile id
     * @return The {@link ProfileResource} resource for the profile
     */
    @GetMapping("/{profileId}")
    @Operation(summary = "Get profile by id", description = "Retrieves a profile by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<?> getProfileById(
            @PathVariable
            @Parameter(description = "Unique profile identifier", example = "1", required = true)
            Long profileId
    ) {
        return profileQueryService.handle(new GetProfileByIdQuery(profileId))
                .map(profile -> ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update the information of a profile.
     *
     * @param profileId The profile id
     * @param resource The {@link UpdateProfileResource} with the new profile data
     * @return The {@link ProfileResource} resource for the updated profile
     */
    @PutMapping("/{profileId}")
    @Operation(summary = "Update a profile", description = "Updates the personal information of a profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<?> updateProfile(
            @PathVariable
            @Parameter(description = "Unique profile identifier", example = "1", required = true)
            Long profileId,
            @RequestBody UpdateProfileResource resource
    ) {
        var command = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var result = profileCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    /**
     * Get the profile of a user.
     *
     * @param userId The user id
     * @return The {@link ProfileResource} resource for the user's profile
     */
    @GetMapping
    @Operation(summary = "Get profile by user", description = "Retrieves the profile of the given user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "The user has no profile")
    })
    public ResponseEntity<?> getProfileByUserId(
            @RequestParam
            @Parameter(description = "User id", example = "1", required = true)
            Long userId
    ) {
        return profileQueryService.handle(new GetProfileByUserIdQuery(new UserId(userId)))
                .map(profile -> ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
