package br.dev.schirmer.thinkon.application.queries;

import br.dev.schirmer.thinkon.application.dtos.UsersResponseDTO;
import br.dev.schirmer.thinkon.application.pipeline.Query;


public record FindUserQuery() implements Query<UsersResponseDTO> {
}
