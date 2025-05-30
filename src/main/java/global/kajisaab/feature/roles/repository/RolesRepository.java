package global.kajisaab.feature.roles.repository;

import global.kajisaab.feature.roles.entity.Roles;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.jpa.reactive.ReactorJpaSpecificationExecutor;
import io.micronaut.data.repository.reactive.ReactorPageableRepository;
import reactor.core.publisher.Flux;

import java.util.List;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface RolesRepository extends ReactorPageableRepository<Roles, String>, ReactorJpaSpecificationExecutor<Roles> {

    Flux<Roles> findAllByIdIn (List<String> id);
}
