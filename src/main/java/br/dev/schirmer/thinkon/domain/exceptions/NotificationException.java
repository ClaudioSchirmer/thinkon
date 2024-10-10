package br.dev.schirmer.thinkon.domain.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class NotificationException extends Throwable {
    private final List<Notification> notifications;

    public NotificationException(List<Notification> notifications) {
        super();
        this.notifications = notifications;
    }

    public NotificationException(String message, List<Notification> notifications) {
        super(message);
        this.notifications = notifications;
    }

    public NotificationException(String message, Throwable cause, List<Notification> notifications) {
        super(message, cause);
        this.notifications = notifications;
    }

    public NotificationException(Throwable cause, List<Notification> notifications) {
        super(cause);
        this.notifications = notifications;
    }
}