package br.dev.schirmer.thinkon.application.exceptions;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationNotificationException extends Exception {
    private final List<Notification> notifications;

    public ApplicationNotificationException(List<Notification> notifications) {
        super();
        this.notifications = notifications;
    }

}