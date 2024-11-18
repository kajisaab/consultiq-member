package global.kajisaab.feature.roles.specification;

import global.kajisaab.core.filterSpecification.AbstractFilterSpecifications;
import global.kajisaab.feature.roles.entity.Roles;
import global.kajisaab.feature.roles.dto.RolesTableFilterQueryDto;

public class RolesFilterSpecification extends AbstractFilterSpecifications<RolesFilterSpecification, Roles> {

    private RolesFilterSpecification() {}

    public static RolesFilterSpecification of() {
        return new RolesFilterSpecification();
    }

    public RolesFilterSpecification globalSearchParam(String searchParam) {
        return super.globalSearch(searchParam, new RolesTableFilterQueryDto());
    }
}

