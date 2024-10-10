package br.dev.schirmer.thinkon.domain.exceptions;

import java.util.List;

public class DomainNotificationException extends NotificationException {

    public DomainNotificationException(List<Notification> notifications) {
        super(notifications);
    }

}