package com.techwatch.techwatchbackend.devices.interfaces.rest;

import com.techwatch.techwatchbackend.devices.application.commandservices.PropertyCommandService;
import com.techwatch.techwatchbackend.devices.application.queryservices.PropertyQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.entities.Space;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetSpaceByPropertyIdAndNameQuery;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.CreateSpaceResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.SpaceResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.CreateSpaceCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.SpaceResourceFromEntityAssembler;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import com.techwatch.techwatchbackend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the management of the spaces of a property.
 */
@RestController
@RequestMapping(value = "/api/v1/properties/{propertyId}/spaces", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Properties", description = "Property spaces management endpoints")
public class PropertySpacesController {
    private final PropertyCommandService propertyCommandService;
    private final PropertyQueryService propertyQueryService;

    public PropertySpacesController(PropertyCommandService propertyCommandService, PropertyQueryService propertyQueryService) {
        this.propertyCommandService = propertyCommandService;
        this.propertyQueryService = propertyQueryService;
    }

    /**
     * Create a new space within a property.
     *
     * @param propertyId The owning property id
     * @param resource The {@link CreateSpaceResource} instance
     * @return The {@link SpaceResource} resource for the created space
     */
    @PostMapping
    @Operation(summary = "Create a space", description = "Creates a new space (ambient) within a property.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Space created successfully",
                    content = @Content(schema = @Schema(implementation = SpaceResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Property not found"),
            @ApiResponse(responseCode = "409", description = "Space name already exists in the property")
    })
    public ResponseEntity<?> createSpace(
            @PathVariable
            @Parameter(description = "Owning property id", example = "1", required = true)
            Long propertyId,
            @RequestBody CreateSpaceResource resource
    ) {
        var command = CreateSpaceCommandFromResourceAssembler.toCommandFromResource(propertyId, resource);
        var result = propertyCommandService.handle(command)
                .flatMap(savedPropertyId -> propertyQueryService.handle(new GetSpaceByPropertyIdAndNameQuery(savedPropertyId, resource.name()))
                        .<Result<Space, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Space", resource.name()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SpaceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
