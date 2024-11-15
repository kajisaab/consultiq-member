package global.kajisaab.feature.auth.repository;

import global.kajisaab.feature.auth.entity.UserCredentialEntity;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface UserCredentialRepository extends ReactorCrudRepository<UserCredentialEntity, String> {

    Mono<UserCredentialEntity> findByUserId(String userId);
}
