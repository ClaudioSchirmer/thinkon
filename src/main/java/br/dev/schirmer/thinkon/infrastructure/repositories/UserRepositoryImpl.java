package br.dev.schirmer.thinkon.infrastructure.repositories;

import br.dev.schirmer.thinkon.domain.ValidEntity;
import br.dev.schirmer.thinkon.domain.entities.User;
import br.dev.schirmer.thinkon.domain.entities.UserRepository;
import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;
import br.dev.schirmer.thinkon.infrastructure.tables.UserTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UUID insertUser(ValidEntity.Insertable<User> user) throws InfrastructureNotificationException {
        try {
            User entity = user.entity();
            UserTable saved = userJpaRepository.save(
                    new UserTable(
                            null,
                            entity.getFirstName().value(),
                            entity.getLastName().value(),
                            entity.getEmail().value(),
                            entity.getPhoneNumber().value()
                    )
            );
            return saved.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InfrastructureNotificationException(Collections.singletonList(new Notification("", "Unknown error when trying to insert a user.")));
        }
    }

    @Override
    public User findById(UUID uuid) throws InfrastructureNotificationException {
        try {
            Optional<UserTable> opItem = userJpaRepository.findById(uuid);
            if (opItem.isPresent()) {
                UserTable item = opItem.get();
                User user = new User(
                        item.getId(),
                        item.getFirstName(),
                        item.getLastName(),
                        item.getEmail(),
                        item.getPhoneNumber()
                );
                return user;
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InfrastructureNotificationException(Collections.singletonList(new Notification("", "Unknown error when trying to find a user.")));
        }
    }

    @Override
    public void delete(ValidEntity.Deletable<User> user) {
        userJpaRepository.deleteById(user.entity().getId());
    }

    @Override
    public void update(ValidEntity.Updatable<User> user) throws InfrastructureNotificationException {
        try {
            UserTable userTable = new UserTable(
                    user.entity().getId(),
                    user.entity().getFirstName().value(),
                    user.entity().getLastName().value(),
                    user.entity().getEmail().value(),
                    user.entity().getPhoneNumber().value()
            );
            userJpaRepository.save(userTable);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InfrastructureNotificationException(Collections.singletonList(new Notification("", "Unknown error when trying to update a user.")));
        }
    }
}