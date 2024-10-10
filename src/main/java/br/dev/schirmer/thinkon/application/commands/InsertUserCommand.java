package br.dev.schirmer.thinkon.application.commands;

import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.pipeline.Command;

public record InsertUserCommand(
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) implements Command<UserResponseDTO> {
}
