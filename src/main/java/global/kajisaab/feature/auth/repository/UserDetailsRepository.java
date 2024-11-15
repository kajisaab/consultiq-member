package global.kajisaab.feature.auth.repository;

import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public abstract class UserDetailsRepository implements ReactorCrudRepository<UserDetailsEntity, String> {

    public abstract Mono<UserDetailsEntity> findByEmail(String email);
}
