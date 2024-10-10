package br.dev.schirmer.thinkon.application.pipeline;

import br.dev.schirmer.thinkon.application.exceptions.ApplicationNotificationException;
import br.dev.schirmer.thinkon.domain.exceptions.DomainNotificationException;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;

public interface Handler<TResult, TRequest extends Request<TResult>> {
    TResult invoke(TRequest request) throws DomainNotificationException, InfrastructureNotificationException, ApplicationNotificationException;
}
