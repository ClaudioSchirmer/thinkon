package br.dev.schirmer.thinkon.infrastructure.exceptions;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import lombok.Getter;

import java.util.List;

@Getter
public class InfrastructureNotificationException extends Throwable {
    private final List<Notification> notifications;

    public InfrastructureNotificationException(List<Notification> notifications) {
        super();
        this.notifications = notifications;
    }

}
