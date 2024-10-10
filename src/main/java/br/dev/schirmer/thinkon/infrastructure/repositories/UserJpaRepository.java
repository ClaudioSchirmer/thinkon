package br.dev.schirmer.thinkon.infrastructure.repositories;

import br.dev.schirmer.thinkon.infrastructure.tables.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserTable, UUID> {
}
