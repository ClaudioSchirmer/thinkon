package br.dev.schirmer.thinkon.web.result;

public record SuccessResult<TResult>(
        TResult data
){}