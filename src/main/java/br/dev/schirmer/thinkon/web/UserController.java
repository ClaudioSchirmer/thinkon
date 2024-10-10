package br.dev.schirmer.thinkon.web;

import br.dev.schirmer.thinkon.application.commands.DeleteUserCommand;
import br.dev.schirmer.thinkon.application.commands.InsertUserCommand;
import br.dev.schirmer.thinkon.application.commands.UpdateUserCommand;
import br.dev.schirmer.thinkon.application.pipeline.Pipeline;
import br.dev.schirmer.thinkon.application.pipeline.Result;
import br.dev.schirmer.thinkon.application.queries.FindUserByIdQuery;
import br.dev.schirmer.thinkon.application.queries.FindUserQuery;
import br.dev.schirmer.thinkon.web.dtos.UserRequestDTO;
import br.dev.schirmer.thinkon.web.result.BadRequestResult;
import br.dev.schirmer.thinkon.web.result.SuccessResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Pipeline pipeline;

    @Autowired
    public UserController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping
    @Operation(summary = "Insert", description = "This endpoint allows the insertion of new users into the system.")
    public ResponseEntity<?> insertUser(@RequestBody InsertUserCommand insertUserCommand) {
        Result<?> result = pipeline.dispatch(insertUserCommand);
        return getResponse(result, null);
    }

    @GetMapping
    @Operation(summary = "Find", description = "This endpoint allows you to retrieve all users from the system.")
    public ResponseEntity<?> getAllUsers() {
        Result<?> result = pipeline.dispatch(new FindUserQuery());
        return getResponse(result, null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find By Id", description = "This endpoint allows you to retrieve a user by ID from the system.")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID uuid) {
        Result<?> result = pipeline.dispatch(new FindUserByIdQuery(uuid));
        return getResponse(result, ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete By Id", description = "This endpoint allows you to delete a user by ID.")
    public ResponseEntity<?> deleteById(@PathVariable("id") UUID uuid) {
        Result<?> result = pipeline.dispatch(new DeleteUserCommand(uuid));
        return getResponse(result, ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update By Id", description = "This endpoint allows you to update a user by ID.")
    public ResponseEntity<?> updateById(@PathVariable("id") UUID uuid, @RequestBody UserRequestDTO userRequestDTO) {
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(
                uuid,
                userRequestDTO.firstName(),
                userRequestDTO.lastName(),
                userRequestDTO.email(),
                userRequestDTO.phoneNumber()
        );
        Result<?> result = pipeline.dispatch(updateUserCommand);
        return getResponse(result, null);
    }

    private ResponseEntity<?> getResponse(Result<?> result, ResponseEntity<?> defaultResponseWhenResultNull) {
        switch (result) {
            case Result.Success<?> successResult -> {
                if (successResult.value() == null && defaultResponseWhenResultNull != null) {
                    return defaultResponseWhenResultNull;
                } else {
                    return ResponseEntity.ok().body(new SuccessResult<>(successResult.value()));
                }
            }
            case Result.Failure failureResult -> {
                return ResponseEntity.badRequest().body(new BadRequestResult(failureResult.notifications()));
            }
            case Result.ExceptionResult exceptionResult -> {
                // Do not expose internal errors to the client.
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
        }
    }
}
