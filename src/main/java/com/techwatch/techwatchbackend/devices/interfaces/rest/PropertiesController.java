package com.techwatch.techwatchbackend.devices.interfaces.rest;

import com.techwatch.techwatchbackend.devices.application.commandservices.PropertyCommandService;
import com.techwatch.techwatchbackend.devices.application.queryservices.PropertyQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetPropertiesByUserIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetPropertyByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.CreatePropertyResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.PropertyResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.CreatePropertyCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.PropertyResourceFromEntityAssembler;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes property registration and lookup endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/properties", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Properties", description = "Property management endpoints")
public class PropertiesController {
    private final PropertyCommandService propertyCommandService;
    private final PropertyQueryService propertyQueryService;

    public PropertiesController(PropertyCommandService propertyCommandService, PropertyQueryService propertyQueryService) {
        this.propertyCommandService = propertyCommandService;
        this.propertyQueryService = propertyQueryService;
    }

    /**
     * Register a new property.
     *
     * @param resource The {@link CreatePropertyResource} instance
     * @return The {@link PropertyResource} resource for the created property
     */
    @PostMapping
    @Operation(summary = "Register a new property", description = "Registers a new property (house or apartment) for a user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Property registered successfully",
                    content = @Content(schema = @Schema(implementation = PropertyResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Property name already exists for the user")
    })
    public ResponseEntity<?> createProperty(@RequestBody CreatePropertyResource resource) {
        var command = CreatePropertyCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = propertyCommandService.handle(command)
                .flatMap(propertyId -> propertyQueryService.handle(new GetPropertyByIdQuery(propertyId))
                        .<Result<Property, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Property", propertyId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PropertyResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a property by id.
     *
     * @param propertyId The property id
     * @return The {@link PropertyResource} resource for the property
     */
    @GetMapping("/{propertyId}")
    @Operation(summary = "Get property by ID", description = "Retrieves a specific property by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Property found",
                    content = @Content(schema = @Schema(implementation = PropertyResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Property not found")
    })
    public ResponseEntity<PropertyResource> getPropertyById(
            @PathVariable
            @Parameter(description = "Unique property identifier", example = "1", required = true)
            Long propertyId
    ) {
        var property = propertyQueryService.handle(new GetPropertyByIdQuery(propertyId));
        if (property.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(PropertyResourceFromEntityAssembler.toResourceFromEntity(property.get()));
    }

    /**
     * Get all properties owned by a user.
     *
     * @param userId The owner user id
     * @return The list of {@link PropertyResource} resources for the user's properties
     */
    @GetMapping
    @Operation(summary = "Get properties by user", description = "Retrieves all properties owned by the given user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Properties retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PropertyResource.class))
            )
    })
    public ResponseEntity<List<PropertyResource>> getPropertiesByUserId(
            @RequestParam
            @Parameter(description = "Owner user id", example = "1", required = true)
            Long userId
    ) {
        var properties = propertyQueryService.handle(new GetPropertiesByUserIdQuery(new UserId(userId)));
        var resources = properties.stream()
                .map(PropertyResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
