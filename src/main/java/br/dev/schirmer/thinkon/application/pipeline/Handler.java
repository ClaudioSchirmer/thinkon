package br.dev.schirmer.thinkon.application.pipeline;

import br.dev.schirmer.thinkon.domain.exceptions.NotificationException;

public interface Handler<TResult, TRequest extends Request<TResult>> {
    TResult invoke(TRequest request) throws NotificationException;
}
