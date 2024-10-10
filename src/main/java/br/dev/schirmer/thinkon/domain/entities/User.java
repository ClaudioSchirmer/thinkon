package br.dev.schirmer.thinkon.domain.entities;

import br.dev.schirmer.thinkon.domain.ValidEntity;
import br.dev.schirmer.thinkon.domain.exceptions.DomainNotificationException;
import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import br.dev.schirmer.thinkon.domain.valueobjects.Email;
import br.dev.schirmer.thinkon.domain.valueobjects.Name;
import br.dev.schirmer.thinkon.domain.valueobjects.PhoneNumber;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {
    private final UUID id;
    private final List<Notification> notifications = new ArrayList<>();

    private Name firstName;
    private Name lastName;
    private Email email;
    private PhoneNumber phoneNumber;

    public User(
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        this.id = null;
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.email = new Email(email);
    }

    public User(
            UUID id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        this.id = id;
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.email = new Email(email);
    }

    public String getUsername() {
        return firstName.value() + " " + lastName.value();
    }

    public ValidEntity.Insertable<User> getInsertable() throws DomainNotificationException {
        checkAndThrow();
        return new ValidEntity.Insertable<>(this);
    }

    public ValidEntity.Updatable<User> getUpdatable() throws DomainNotificationException {
        checkAndThrow();
        return new ValidEntity.Updatable<>(this);
    }

    public ValidEntity.Deletable<User> getDeletable() throws DomainNotificationException {
        // Rules for deleting the user.
        return new ValidEntity.Deletable<>(this);
    }

    private void checkAndThrow() throws DomainNotificationException {
        firstName.validate(true, "firstName", notifications);
        lastName.validate(true, "lastName", notifications);
        email.validate(true, "email", notifications);
        phoneNumber.validate(true, "phoneNumber", notifications);
        if (!notifications.isEmpty()) {
            throw new DomainNotificationException(notifications);
        }
    }
}
