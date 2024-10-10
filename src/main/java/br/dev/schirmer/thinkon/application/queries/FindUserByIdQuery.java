package br.dev.schirmer.thinkon.application.queries;

import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.pipeline.Query;

import java.util.UUID;

public record FindUserByIdQuery(
        UUID uuid
) implements Query<UserResponseDTO> {
}
