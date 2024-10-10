package br.dev.schirmer.thinkon.application.dtos;

import java.util.List;

public record UsersResponseDTO(
        List<UserResponseDTO> users
) {
}
