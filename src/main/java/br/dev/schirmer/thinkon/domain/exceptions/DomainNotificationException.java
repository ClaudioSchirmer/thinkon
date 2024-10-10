package br.dev.schirmer.thinkon.domain.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class DomainNotificationException extends Exception {
    private final List<Notification> notifications;

    public DomainNotificationException(List<Notification> notifications) {
        super();
        this.notifications = notifications;
    }

}