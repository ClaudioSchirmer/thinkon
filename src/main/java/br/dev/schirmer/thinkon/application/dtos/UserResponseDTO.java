package br.dev.schirmer.thinkon.application.dtos;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
