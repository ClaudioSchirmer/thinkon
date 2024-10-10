package br.dev.schirmer.thinkon.infrastructure.repositories;

import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.queries.UserQueriesRepository;
import br.dev.schirmer.thinkon.infrastructure.tables.UserTable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserQueriesRepositoryImpl implements UserQueriesRepository {

    private final UserJpaRepository userJpaRepository;

    public UserQueriesRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public List<UserResponseDTO> findAll() {
        return userJpaRepository.findAll().stream().map( item ->
                new UserResponseDTO(
                        item.getId(),
                        item.getUserName(),
                        item.getFirstName(),
                        item.getLastName(),
                        item.getEmail(),
                        item.getPhoneNumber()
                )
        ).collect(Collectors.toList());
    }

    public UserResponseDTO findById(UUID id) {
        Optional<UserTable> opItem = userJpaRepository.findById(id);
        if (opItem.isPresent()) {
            UserTable item = opItem.get();
            return new UserResponseDTO(
                    item.getId(),
                    item.getUserName(),
                    item.getFirstName(),
                    item.getLastName(),
                    item.getEmail(),
                    item.getPhoneNumber()
            );
        }
        return null;
    }

}
