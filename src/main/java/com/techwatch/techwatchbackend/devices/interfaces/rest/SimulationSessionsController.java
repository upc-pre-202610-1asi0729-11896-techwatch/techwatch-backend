package com.techwatch.techwatchbackend.devices.interfaces.rest;

import com.techwatch.techwatchbackend.devices.application.commandservices.SimulationSessionCommandService;
import com.techwatch.techwatchbackend.devices.application.queryservices.SimulationSessionQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.commands.EndSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetActiveSimulationSessionByUserIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetSimulationSessionByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.SimulationSessionResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.StartSimulationSessionResource;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.SimulationSessionResourceFromEntityAssembler;
import com.techwatch.techwatchbackend.devices.interfaces.rest.transform.StartSimulationSessionCommandFromResourceAssembler;
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
 * REST controller that exposes simulation session endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/simulation-sessions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Simulation Sessions", description = "Device simulation session endpoints")
public class SimulationSessionsController {
    private final SimulationSessionCommandService simulationSessionCommandService;
    private final SimulationSessionQueryService simulationSessionQueryService;

    public SimulationSessionsController(SimulationSessionCommandService simulationSessionCommandService,
                                        SimulationSessionQueryService simulationSessionQueryService) {
        this.simulationSessionCommandService = simulationSessionCommandService;
        this.simulationSessionQueryService = simulationSessionQueryService;
    }

    /**
     * Start a new simulation session.
     *
     * @param resource The {@link StartSimulationSessionResource} instance
     * @return The {@link SimulationSessionResource} resource for the created session
     */
    @PostMapping
    @Operation(summary = "Start a simulation session", description = "Starts a new simulation session for a user on a property. A user can only have one active session at a time.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Simulation session started successfully",
                    content = @Content(schema = @Schema(implementation = SimulationSessionResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "User already has an active simulation session")
    })
    public ResponseEntity<?> startSimulationSession(@RequestBody StartSimulationSessionResource resource) {
        var command = StartSimulationSessionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = simulationSessionCommandService.handle(command)
                .flatMap(sessionId -> simulationSessionQueryService.handle(new GetSimulationSessionByIdQuery(sessionId))
                        .<Result<SimulationSession, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("SimulationSession", sessionId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SimulationSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * End an active simulation session.
     *
     * @param sessionId The session id
     * @return The {@link SimulationSessionResource} resource for the ended session
     */
    @PostMapping("/{sessionId}/end")
    @Operation(summary = "End a simulation session", description = "Ends an active simulation session, setting its status to ENDED.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Simulation session ended successfully",
                    content = @Content(schema = @Schema(implementation = SimulationSessionResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Simulation session not found"),
            @ApiResponse(responseCode = "422", description = "The simulation session is already ended")
    })
    public ResponseEntity<?> endSimulationSession(
            @PathVariable
            @Parameter(description = "Simulation session id", example = "1", required = true)
            Long sessionId
    ) {
        var result = simulationSessionCommandService.handle(new EndSimulationSessionCommand(sessionId));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SimulationSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Get a simulation session by id.
     *
     * @param sessionId The session id
     * @return The {@link SimulationSessionResource} resource for the session
     */
    @GetMapping("/{sessionId}")
    @Operation(summary = "Get simulation session by ID", description = "Retrieves a specific simulation session by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Simulation session found",
                    content = @Content(schema = @Schema(implementation = SimulationSessionResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Simulation session not found")
    })
    public ResponseEntity<SimulationSessionResource> getSimulationSessionById(
            @PathVariable
            @Parameter(description = "Unique session identifier", example = "1", required = true)
            Long sessionId
    ) {
        var session = simulationSessionQueryService.handle(new GetSimulationSessionByIdQuery(sessionId));
        if (session.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(SimulationSessionResourceFromEntityAssembler.toResourceFromEntity(session.get()));
    }

    /**
     * Get the active simulation session of a user.
     *
     * @param userId The user id
     * @return The {@link SimulationSessionResource} resource for the active session
     */
    @GetMapping("/active")
    @Operation(summary = "Get active simulation session by user", description = "Retrieves the active simulation session of the given user, if any.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Active simulation session found",
                    content = @Content(schema = @Schema(implementation = SimulationSessionResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "No active simulation session for the user")
    })
    public ResponseEntity<SimulationSessionResource> getActiveSimulationSession(
            @RequestParam
            @Parameter(description = "User id", example = "1", required = true)
            Long userId
    ) {
        var session = simulationSessionQueryService.handle(new GetActiveSimulationSessionByUserIdQuery(new UserId(userId)));
        if (session.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(SimulationSessionResourceFromEntityAssembler.toResourceFromEntity(session.get()));
    }
}
