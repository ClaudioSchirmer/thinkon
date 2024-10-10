package br.dev.schirmer.thinkon.application.exceptions;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import br.dev.schirmer.thinkon.domain.exceptions.NotificationException;

import java.util.List;

public class ApplicationNotificationException extends NotificationException {

    public ApplicationNotificationException(List<Notification> notifications) {
        super(notifications);
    }

    public ApplicationNotificationException() {
        super(null);
    }

}