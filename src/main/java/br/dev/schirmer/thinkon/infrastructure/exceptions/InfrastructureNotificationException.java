package br.dev.schirmer.thinkon.infrastructure.exceptions;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import br.dev.schirmer.thinkon.domain.exceptions.NotificationException;

import java.util.List;

public class InfrastructureNotificationException extends NotificationException {

    public InfrastructureNotificationException(List<Notification> notifications) {
        super(notifications);
    }

}
