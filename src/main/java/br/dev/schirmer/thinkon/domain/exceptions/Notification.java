package br.dev.schirmer.thinkon.domain.exceptions;

public record Notification(
   String fieldName,
   String notification
) {}
