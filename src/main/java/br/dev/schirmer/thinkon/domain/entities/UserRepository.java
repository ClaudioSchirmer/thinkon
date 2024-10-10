package br.dev.schirmer.thinkon.domain.entities;

import br.dev.schirmer.thinkon.domain.ValidEntity;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;

import java.util.UUID;

public interface UserRepository {
    UUID insertUser(ValidEntity.Insertable<User> user) throws InfrastructureNotificationException;
    User findById(UUID uuid) throws InfrastructureNotificationException;
    void delete(ValidEntity.Deletable<User> user) throws InfrastructureNotificationException;
    void update(ValidEntity.Updatable<User> user) throws InfrastructureNotificationException;
}
