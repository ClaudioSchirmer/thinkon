package br.dev.schirmer.thinkon.web.result;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;

public record BadRequestResult(
        List<Notification> notifications
) {
}
