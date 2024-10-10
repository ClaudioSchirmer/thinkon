package br.dev.schirmer.thinkon.application.queries.handlers;

import br.dev.schirmer.thinkon.application.dtos.UserResponseDTO;
import br.dev.schirmer.thinkon.application.pipeline.Handler;
import br.dev.schirmer.thinkon.application.queries.FindUserByIdQuery;
import br.dev.schirmer.thinkon.infrastructure.repositories.UserQueriesRepositoryImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class FindUserByIdQueryHandler implements Handler<UserResponseDTO, FindUserByIdQuery> {

    private final UserQueriesRepositoryImpl userQueriesImpl;

    public FindUserByIdQueryHandler(UserQueriesRepositoryImpl userQueriesImpl) {
        this.userQueriesImpl = userQueriesImpl;
    }

    @Override
    public UserResponseDTO invoke(FindUserByIdQuery request) {
        return userQueriesImpl.findById(request.uuid());
    }
}
