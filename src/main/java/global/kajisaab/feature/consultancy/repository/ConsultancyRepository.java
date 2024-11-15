package global.kajisaab.feature.consultancy.repository;

import global.kajisaab.feature.consultancy.dto.ConsultancySchemaGeneratorDto;
import global.kajisaab.feature.consultancy.entity.ConsultancyEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public abstract class ConsultancyRepository implements ReactorCrudRepository<ConsultancyEntity, String> {

    @Query(value = "select * from public.prepare_consultancy_schema(:consultancyCode, CAST(:consultancySchemaGeneratorDto AS jsonb))", nativeQuery = true)
    public abstract Mono<Boolean> generateConsultancySchema(String consultancyCode, String consultancySchemaGeneratorDto);
}
