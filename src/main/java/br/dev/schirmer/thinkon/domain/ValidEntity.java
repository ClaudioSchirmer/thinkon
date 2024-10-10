package br.dev.schirmer.thinkon.domain;

public sealed interface ValidEntity<TEntity> permits ValidEntity.Deletable, ValidEntity.Insertable, ValidEntity.Updatable {
    record Insertable<TEntity>(TEntity entity) implements ValidEntity<TEntity>{}
    record Updatable<TEntity>(TEntity entity) implements ValidEntity<TEntity>{}
    record Deletable<TEntity>(TEntity entity) implements ValidEntity<TEntity>{}
}
