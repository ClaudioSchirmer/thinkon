package br.dev.schirmer.thinkon.application.commands.handlers;

import br.dev.schirmer.thinkon.application.commands.UpdateUserCommand;
import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.exceptions.ApplicationNotificationException;
import br.dev.schirmer.thinkon.application.pipeline.Handler;
import br.dev.schirmer.thinkon.domain.entities.User;
import br.dev.schirmer.thinkon.domain.exceptions.DomainNotificationException;
import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import br.dev.schirmer.thinkon.domain.valueobjects.Email;
import br.dev.schirmer.thinkon.domain.valueobjects.Name;
import br.dev.schirmer.thinkon.domain.valueobjects.PhoneNumber;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;
import br.dev.schirmer.thinkon.infrastructure.repositories.UserRepositoryImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Lazy
public class UpdateUserCommandHandler implements Handler<UserResponseDTO, UpdateUserCommand> {
    private final UserRepositoryImpl userRepositoryImpl;

    public UpdateUserCommandHandler(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public UserResponseDTO invoke(UpdateUserCommand request) throws DomainNotificationException, InfrastructureNotificationException, ApplicationNotificationException {
        User user = userRepositoryImpl.findById(request.uuid());
        if (user == null) {
            throw new ApplicationNotificationException(Collections.singletonList(new Notification("id", "User not found.")));
        }
        if (request.firstName() != null) {
            user.setFirstName(new Name(request.firstName()));
        }
        if (request.lastName() != null) {
            user.setLastName(new Name(request.lastName()));
        }
        if (request.email() != null) {
            user.setEmail(new Email(request.email()));
        }
        if (request.phoneNumber() != null) {
            user.setPhoneNumber(new PhoneNumber(request.phoneNumber()));
        }
        userRepositoryImpl.update(user.getUpdatable());
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName().value(),
                user.getLastName().value(),
                user.getEmail().value(),
                user.getPhoneNumber().value()
        );
    }
}
