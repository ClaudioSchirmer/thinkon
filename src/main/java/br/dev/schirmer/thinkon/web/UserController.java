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
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Insert", description = "This endpoint allows the insertion of new users.")
    public ResponseEntity<?> insertUser(@RequestBody InsertUserCommand insertUserCommand) {
        Result<?> result = pipeline.dispatch(insertUserCommand);
        switch (result) {
            case Result.Success<?> successResult -> {
                return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult<>(successResult.value()));
            }
            case Result.Failure failureResult -> {
                return ResponseEntity.badRequest().body(new BadRequestResult(failureResult.notifications()));
            }
            case Result.Exception exception -> {
                log.error(exception.exception().toString(), exception.exception());
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
        }
    }

    @GetMapping
    @Operation(summary = "Find", description = "This endpoint allows you to retrieve all users.")
    public ResponseEntity<?> getAllUsers() {
        Result<?> result = pipeline.dispatch(new FindUserQuery());
        switch (result) {
            case Result.Success<?> successResult -> {
                return ResponseEntity.ok().body(new SuccessResult<>(successResult.value()));
            }
            case Result.Failure failureResult -> {
                return ResponseEntity.badRequest().body(new BadRequestResult(failureResult.notifications()));
            }
            case Result.Exception exception -> {
                log.error(exception.exception().toString(), exception.exception());
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find By Id", description = "This endpoint allows you to retrieve a user by ID.")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID uuid) {
        Result<?> result = pipeline.dispatch(new FindUserByIdQuery(uuid));
        switch (result) {
            case Result.Success<?> successResult -> {
                if (successResult.value() == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok().body(new SuccessResult<>(successResult.value()));
            }
            case Result.Failure failureResult -> {
                return ResponseEntity.badRequest().body(new BadRequestResult(failureResult.notifications()));
            }
            case Result.Exception exception -> {
                log.error(exception.exception().toString(), exception.exception());
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete By Id", description = "This endpoint allows you to delete a user by ID.")
    public ResponseEntity<?> deleteById(@PathVariable("id") UUID uuid) {
        Result<?> result = pipeline.dispatch(new DeleteUserCommand(uuid));
        switch (result) {
            case Result.Success<?> successResult -> {
                return ResponseEntity.noContent().build();
            }
            case Result.Failure failureResult -> {
                if (failureResult.notifications() == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.badRequest().body(new BadRequestResult(failureResult.notifications()));
            }
            case Result.Exception exception -> {
                log.error(exception.exception().toString(), exception.exception());
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
        }
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
        switch (result) {
            case Result.Success<?> successResult -> {
                return ResponseEntity.ok().body(new SuccessResult<>(successResult.value()));
            }
            case Result.Failure failureResult -> {
                if (failureResult.notifications() == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.badRequest().body(new BadRequestResult(failureResult.notifications()));
            }
            case Result.Exception exception -> {
                log.error(exception.exception().toString(), exception.exception());
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
        }
    }
}
