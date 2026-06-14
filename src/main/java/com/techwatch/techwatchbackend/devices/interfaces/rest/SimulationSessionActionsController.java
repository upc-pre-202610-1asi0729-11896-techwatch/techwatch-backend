package com.techwatch.techwatchbackend.devices.interfaces.rest;

import com.techwatch.techwatchbackend.devices.application.commandservices.SimulationSessionCommandService;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.RecordDeviceActionResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.SimulationSessionResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.RecordDeviceActionCommandFromResourceAssembler;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.SimulationSessionResourceFromEntityAssembler;
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
 * REST controller that exposes the recording of device actions within a simulation session.
 */
@RestController
@RequestMapping(value = "/api/v1/simulation-sessions/{sessionId}/actions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Simulation Sessions", description = "Device simulation session endpoints")
public class SimulationSessionActionsController {
    private final SimulationSessionCommandService simulationSessionCommandService;

    public SimulationSessionActionsController(SimulationSessionCommandService simulationSessionCommandService) {
        this.simulationSessionCommandService = simulationSessionCommandService;
    }

    /**
     * Record a device action in a simulation session.
     *
     * @param sessionId The session id
     * @param resource The {@link RecordDeviceActionResource} instance
     * @return The {@link SimulationSessionResource} resource for the updated session
     */
    @PostMapping
    @Operation(summary = "Record a device action",
            description = "Records an action against a device during an active simulation session, generating usage data and publishing a usage data generated event.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Device action recorded successfully",
                    content = @Content(schema = @Schema(implementation = SimulationSessionResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Simulation session or device not found"),
            @ApiResponse(responseCode = "422", description = "The simulation session is not active")
    })
    public ResponseEntity<?> recordDeviceAction(
            @PathVariable
            @Parameter(description = "Simulation session id", example = "1", required = true)
            Long sessionId,
            @RequestBody RecordDeviceActionResource resource
    ) {
        var command = RecordDeviceActionCommandFromResourceAssembler.toCommandFromResource(sessionId, resource);
        var result = simulationSessionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SimulationSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
