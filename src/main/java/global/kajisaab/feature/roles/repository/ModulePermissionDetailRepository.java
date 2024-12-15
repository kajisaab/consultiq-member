package global.kajisaab.feature.roles.repository;

import global.kajisaab.feature.roles.entity.ModulePermissionDetail;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public abstract class ModulePermissionDetailRepository implements ReactorCrudRepository<ModulePermissionDetail, String> {
    @Inject
    private ConnectionFactory connectionFactory;


    public Mono<List<Map<String, Object>>> getRolesDetailsUI(String memberCode) {
        String tableName = "consultancy_" + memberCode + ".module_permission_detail";
        String query = String.format("""
                WITH RECURSIVE permission_tree AS (
                    SELECT
                        id, value, label, type, parent_id, position
                    FROM
                        %s
                    WHERE
                        parent_id IS NULL
                    UNION ALL
                    SELECT
                        p.id, p.value, p.label, p.type, p.parent_id, p.position
                    FROM
                        %s p
                    INNER JOIN
                        %s pt ON p.parent_id = pt.id
                )
                SELECT * FROM permission_tree ORDER BY position ASC;
                """, tableName, tableName, tableName);

        return Mono.from(connectionFactory.create())
                .flatMapMany(connection ->
                        Flux.from(connection.createStatement(query).execute())
                                .flatMap(result -> result.map((Row row, RowMetadata metadata) -> mapRowToRolesDetails(row)))
                )
                .collectList()
                .map(this::buildTreeStructure);
    }


    private ModulePermissionDetail mapRowToRolesDetails(Row row) {
        ModulePermissionDetail modulePermissionDetail = new ModulePermissionDetail();
        modulePermissionDetail.setId(row.get("id", String.class));
        modulePermissionDetail.setValue(row.get("value", String.class));
        modulePermissionDetail.setLabel(row.get("label", String.class));
        modulePermissionDetail.setType(row.get("type", String.class));
        modulePermissionDetail.setParentId(row.get("parent_id", String.class));
        modulePermissionDetail.setPosition(row.get("position", Integer.class));
        return modulePermissionDetail;
    }

    private List<Map<String, Object>> buildTreeStructure(List<ModulePermissionDetail> flatData) {
        Map<String, Map<String, Object>> idToNodeMap = new HashMap<>();
        List<Map<String, Object>> tree = new ArrayList<>();

        // Initialize nodes
        flatData.forEach(item -> {
            Map<String, Object> node = new HashMap<>();
            node.put("value", item.getValue());
            node.put("label", item.getLabel());
            node.put("permissions", new ArrayList<>());
            node.put("position", item.getPosition());
            node.put("groups", new ArrayList<>());
            idToNodeMap.put(item.getId(), node);
        });

        // Build hierarchy
        flatData.forEach(item -> {
            // Retrieve the parent node from the map
            Map<String, Object> parentNode = idToNodeMap.get(item.getParentId());
            if (parentNode != null && item.getType().equalsIgnoreCase("GROUP")) {
                ((List<Map<String, Object>>) parentNode.get("groups")).add(idToNodeMap.get(item.getId()));
            }
        });

        flatData.forEach(item -> {
            // Retrieve the parent node from the map
            Map<String, Object> parentNode = idToNodeMap.get(item.getParentId());
            if (parentNode != null && item.getType().equalsIgnoreCase("PERMISSION")) {
                Map<String, Object> permissionDetails = new HashMap<>();
                permissionDetails.put("value", item.getValue());
                permissionDetails.put("label", item.getLabel());
                ((List<Map<String, Object>>) parentNode.get("permissions")).add(permissionDetails);
            }
        });

        flatData.forEach(item -> {
            if (item.getParentId() == null) {
                tree.add(idToNodeMap.get(item.getId()));
            }
        });

        return tree;
    }
}
