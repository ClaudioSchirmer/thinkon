package br.dev.schirmer.thinkon.application.commands.handlers;

import br.dev.schirmer.thinkon.application.commands.DeleteUserCommand;
import br.dev.schirmer.thinkon.application.exceptions.ApplicationNotificationException;
import br.dev.schirmer.thinkon.application.pipeline.Handler;
import br.dev.schirmer.thinkon.domain.entities.User;
import br.dev.schirmer.thinkon.domain.entities.UserRepository;
import br.dev.schirmer.thinkon.domain.exceptions.DomainNotificationException;
import br.dev.schirmer.thinkon.domain.exceptions.Notification;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Lazy
public class DeleteUserCommandHandler implements Handler<Void, DeleteUserCommand> {
    private final UserRepository userRepository;

    public DeleteUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void invoke(DeleteUserCommand request) throws DomainNotificationException, InfrastructureNotificationException, ApplicationNotificationException {
        User user = userRepository.findById(request.uuid());
        if (user == null) {
            throw new ApplicationNotificationException(Collections.singletonList(new Notification("id", "User not found.")));
        }
        userRepository.delete(user.getDeletable());
        return null;
    }
}
