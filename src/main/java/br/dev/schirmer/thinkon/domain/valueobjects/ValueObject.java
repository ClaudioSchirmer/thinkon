package br.dev.schirmer.thinkon.domain.valueobjects;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;

public interface ValueObject<T> {
    T value();
    void validate(Boolean required, String fieldName, List<Notification> notifications);
}
