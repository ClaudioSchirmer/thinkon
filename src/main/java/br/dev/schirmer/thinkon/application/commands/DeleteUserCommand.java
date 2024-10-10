package br.dev.schirmer.thinkon.application.commands;

import br.dev.schirmer.thinkon.application.pipeline.Command;

import java.util.UUID;

public record DeleteUserCommand(
        UUID uuid
) implements Command<Void> {
}
