package br.dev.schirmer.thinkon.application.commands.handlers;

import br.dev.schirmer.thinkon.application.commands.InsertUserCommand;
import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.pipeline.Handler;
import br.dev.schirmer.thinkon.domain.ValidEntity;
import br.dev.schirmer.thinkon.domain.entities.User;
import br.dev.schirmer.thinkon.domain.exceptions.DomainNotificationException;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;
import br.dev.schirmer.thinkon.infrastructure.repositories.UserRepositoryImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class InsertUserCommandHandler implements Handler<UserResponseDTO, InsertUserCommand> {
    private final UserRepositoryImpl userRepositoryImpl;

    public InsertUserCommandHandler(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public UserResponseDTO invoke(InsertUserCommand command) throws DomainNotificationException, InfrastructureNotificationException {
        User user = new User(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phoneNumber()
        );
        ValidEntity.Insertable<User> insertable = user.getInsertable();
        return new UserResponseDTO(
                userRepositoryImpl.insertUser(insertable),
                user.getUsername(),
                user.getFirstName().value(),
                user.getLastName().value(),
                user.getEmail().value(),
                user.getPhoneNumber().value()
        );
    }
}