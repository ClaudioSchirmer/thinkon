package br.dev.schirmer.thinkon.application.pipeline;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;

public sealed interface Result<TResult> permits Result.Success, Result.Failure, Result.ExceptionResult {
    record Success<TResult>(TResult value) implements Result<TResult> {}
    record Failure(List<Notification> notifications) implements Result<Void> {}
    record ExceptionResult(Throwable exception) implements Result<Void> {}
}