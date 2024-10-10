package br.dev.schirmer.thinkon.application.commands;

import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.pipeline.Command;

import java.util.UUID;

public record UpdateUserCommand(
        UUID uuid,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) implements Command<UserResponseDTO> {
}
