package br.dev.schirmer.thinkon.web.dtos;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
