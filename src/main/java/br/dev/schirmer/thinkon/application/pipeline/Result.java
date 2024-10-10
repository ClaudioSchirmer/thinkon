package br.dev.schirmer.thinkon.application.pipeline;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;

public sealed interface Result<TResult> permits Result.Success, Result.Failure, Result.Exception {
    record Success<TResult>(TResult value) implements Result<TResult> {}
    record Failure(List<Notification> notifications) implements Result<Void> {}
    record Exception(Throwable exception) implements Result<Void> {}
}